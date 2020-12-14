package dto.entity;


import dto.IDataTransformSina;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : Roc
 * @date : 2020-12-07 16:17
 * @description : 基金
 **/
public class FundStock extends BaseData implements IDataTransformSina {
    public FundStock() {
        super(DATA_URL);
    }

    @Override
    public void resetCodes() {
        String[] fundStockCodes = config.getFundCodes().split(",");
        this.paramCodes = Arrays.stream(fundStockCodes).map(sz -> "s_" + sz).collect(Collectors.joining(","));
    }

    @Override
    void initParse(Map<String, Function<String, String[]>> map) {
        map.put("var hq_str_s_", CN_FUND_TSF);
    }


}
