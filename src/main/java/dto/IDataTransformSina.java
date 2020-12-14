package dto;

import config.AppSettingState;
import util.PinyinUtils;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author : Roc
 * @date : 2020-12-14 16:01
 * @description : 新浪数据转换接口
 **/
public interface IDataTransformSina {

     String DATA_URL = "https://hq.sinajs.cn/list=";
    /**
     * 隐秘模式处理中文,如果接入多种数据，可抽到父接口
     */
    Function<String, String> HIDDEN_MODE = s -> AppSettingState.getInstance().getHiddenMode() ? PinyinUtils.toPinyin(s) : s;

    /**
     * 国内A股指数数据转换
     */
    Function<String, String[]> CN_INDEX_TSF = s  -> {
        String[] array = s.replace("var hq_str_s_", "")
                .split(",|(=\")");
        BigDecimal vol = new BigDecimal(array[5]).divide(BigDecimal.valueOf(1000000), 3, BigDecimal.ROUND_DOWN);
        BigDecimal amount = new BigDecimal(array[6]).divide(BigDecimal.valueOf(10000), 3, BigDecimal.ROUND_DOWN);
        String temp = array[0];
        array[0] = HIDDEN_MODE.apply(array[1]);
        array[1] = temp;
        array[4] = array[4] + "%";
        array[5] = vol.toString();
        array[6] = amount.toString();
        return array;
    };
    /**
     * 国内基金数据转换
     */
    Function<String, String[]> CN_FUND_TSF = s  -> {
        String[] array = s.replace("var hq_str_s_", "")
                .split(",|(=\")");
        BigDecimal vol = new BigDecimal(array[5]).divide(BigDecimal.valueOf(10000), 3, BigDecimal.ROUND_DOWN);
        BigDecimal amount = new BigDecimal(array[6]);
        String temp = array[0];
        array[0] = HIDDEN_MODE.apply(array[1]);
        array[1] = temp;
        array[4] = array[4] + "%";
        array[5] = vol.toString();
        array[6] = amount.toString();
        return array;
    };
    /**
     * 国内数据转换A股数据
     */
    Function<String, String[]> CN_TSF = s  -> {
        String[] array = s.replace("var hq_str_s_", "")
                .split(",|(=\")");
        BigDecimal vol = new BigDecimal(array[5]).divide(BigDecimal.valueOf(10000), 3, BigDecimal.ROUND_DOWN);
        BigDecimal amount = new BigDecimal(array[6]).divide(BigDecimal.valueOf(10000), 3, BigDecimal.ROUND_DOWN);
        String temp = array[0];
        array[0] = HIDDEN_MODE.apply(array[1]);
        array[1] = temp;
        array[4] = array[4] + "%";
        array[5] = vol.toString();
        array[6] = amount.toString();
        return array;
    };
    /**
     * 美股指数数据转换
     */
    Function<String, String[]> USA_INDEX_TSF = s -> {
        String[] array = s.replace("var hq_str_gb_", "")
                .split(",|(=\")");

        BigDecimal vol = BigDecimal.valueOf(Double.parseDouble(array[11])).divide(BigDecimal.valueOf(100000000), 3, BigDecimal.ROUND_DOWN);
        return new String[]{HIDDEN_MODE.apply(array[1]), array[0], array[2], array[5], array[3] + "%",  vol.toString(),""};

    };
    /**
     * 美股数据转换
     */
    Function<String, String[]> USA_TSF = s -> {
        String[] array = s.replace("var hq_str_gb_", "")
                .split(",|(=\")");

        BigDecimal vol = BigDecimal.valueOf(Double.parseDouble(array[11])).divide(BigDecimal.valueOf(10000), 3, BigDecimal.ROUND_DOWN);
        return new String[]{HIDDEN_MODE.apply(array[1]), array[0], array[2], array[5], array[3] + "%",  vol.toString(),""};

    };
    /**
     * 港股指数数据转换
     */
    Function<String, String[]> HK_INDEX_TSF = s -> {
        String[] array = s.replace("var hq_str_rt_", "")
                .split(",|(=\")");
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(array[12])).divide(BigDecimal.valueOf(100000), 3, BigDecimal.ROUND_DOWN);
        return new String[]{HIDDEN_MODE.apply(array[2]), array[0], array[7], array[8], array[9] + "%", "", amount.toString()};
    };
    /**
     * 港股数据转换
     */
    Function<String, String[]> HK_TSF = s -> {
        String[] array = s.replace("var hq_str_rt_", "")
                .split(",|(=\")");
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(array[12])).divide(BigDecimal.valueOf(100000000), 3, BigDecimal.ROUND_DOWN);
        BigDecimal vol = BigDecimal.valueOf(Double.parseDouble(array[13])).divide(BigDecimal.valueOf(10000), 3, BigDecimal.ROUND_DOWN);
        return new String[]{HIDDEN_MODE.apply(array[2]), array[0], array[7], array[8], array[9] + "%", vol.toString(), amount.toString()};
    };
    /**
     * 期货指数数据转换
     */
    Function<String, String[]> FUTURE_TSF = s -> {
        String[] dataArray = s.replace("var hq_str_hf_", "")
                .split(",|(=\")");
        BigDecimal prePrice = new BigDecimal(dataArray[8]);
        BigDecimal nowPrice = new BigDecimal(dataArray[1]);
        BigDecimal subPrice = nowPrice.subtract(prePrice).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal rate = subPrice.multiply(BigDecimal.valueOf(100)).divide(prePrice, 2, BigDecimal.ROUND_DOWN);
        return new String[]{HIDDEN_MODE.apply("小道琼"), "YM", nowPrice.toString(), subPrice.toString(), rate + "%", "", ""};
    };
}
