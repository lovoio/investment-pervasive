package dto.entity;

import config.AppSettingState;
import config.HttpClientPool;
import util.PinyinUtils;
import window.WindowFactory;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author : Roc
 * @date : 2020-12-08 9:23
 * @description : 基本的数据传输对象
 **/
public abstract class BaseData {
    /**
     * 全局配置
     */
    protected AppSettingState config = AppSettingState.getInstance();

    public BaseData(String uri) {
        this.uri = uri;
        initParse(hashMap);
        resetCodes();
    }

    /**
     * 所有子类共享，相同uri的可以节省http请求
     */
    private  Map<String, Function<String, String[]>> hashMap = new HashMap<>(8);
    /**
     * 接口要请求的参数代码
     */
    protected String paramCodes;
    /**
     * 接口要请求的uri
     */
    private String uri;

    /**
     * 重置接口参数编码
     */
    public abstract void resetCodes();

    /**
     * 接口数据解析配置
     *
     * @param hashMap 数据转换的函数map
     */
    abstract void initParse(Map<String, Function<String, String[]>> hashMap);

    public String getUri() {
        return uri;
    }

    /**
     * 解析数据
     *
     * @param dataArray 数据的数组集合
     * @return 处理后对应行列的数据集合
     */
    public List<String[]> parse(String[] dataArray) {
        List<String[]> arrayList = new ArrayList<>();
        for (String s : dataArray) {
            String substring = s.substring(0, s.lastIndexOf("_") + 1);
            Function<String, String[]> function = hashMap.get(substring);
            if (function != null) {
                String[] array = function.apply(s);
                arrayList.add(array);
            }
        }
        return arrayList;
    }

    /**
     * 获取数据
     *
     * @return 处理后的行列数据集合
     */
    public List<String[]> getData() {
        try {
            String result = HttpClientPool.INSTANCE.get(this.uri + this.paramCodes);
            String[] dataArray = result.split("\";\n");
            if (dataArray == null || dataArray.length < 1) {
                WindowFactory.logger.info(this.uri + this.paramCodes + "---获取数据为空:" +result);
                return null;
            }
            return parse(dataArray);
        } catch (Exception e) {
            WindowFactory.logger.info(this.uri + this.paramCodes + "---获取数据异常:" + e.getMessage());
            return null;
        }
    }
}
