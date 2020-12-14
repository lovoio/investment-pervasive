package dto.entity;


import dto.IDataTransformSina;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : Roc
 * @date : 2020-12-07 16:17
 * @description : a股市场数据
 **/
public class CnDataStock extends BaseData implements IDataTransformSina {

    public CnDataStock() {
        super(DATA_URL);
    }

    @Override
    public void resetCodes() {
        String[] shStockCodes = config.getShStockCodes().split(",");
        String[] szStockCodes = config.getSzStockCodes().split(",");
        Stream<String> shStream = Arrays.stream(shStockCodes).map(sh -> "s_sh" + sh);
        Stream<String> szStream = Arrays.stream(szStockCodes).map(sz -> "s_sz" + sz);
        this.paramCodes = Stream.concat(szStream, shStream).collect(Collectors.joining(","));
    }

    @Override
    void initParse(Map<String, Function<String, String[]>> map) {
        map.put("var hq_str_s_", CN_TSF);
    }

}
