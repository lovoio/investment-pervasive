package dto.entity;


import dto.IDataTransformSina;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : Roc
 * @date : 2020-12-07 16:17
 * @description : 美股数据对象
 **/
public class UsaDataStock extends BaseData implements IDataTransformSina {

    public UsaDataStock() {
        super(DATA_URL);
    }

    @Override
    public void resetCodes() {
        String[] usaStockCodes = config.getUsaStockCodes().split(",");
        this.paramCodes = Arrays.stream(usaStockCodes).map(sz -> "gb_" + sz).collect(Collectors.joining(","));
    }

    @Override
    void initParse(Map<String, Function<String, String[]>> map) {
        map.put("var hq_str_gb_", USA_TSF);
    }

}
