package entity.dto;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : Roc
 * @date : 2020-12-07 16:17
 * @description : a股市场
 **/
public class HkDataStock extends BaseData {

    public HkDataStock() {
        super("https://hq.sinajs.cn/list=");
    }


    @Override
    public void resetCodes() {
        String[] hkStockCodes = config.getHkStockCodes().split(",");
        this.paramCodes = Arrays.stream(hkStockCodes).map(sz -> "rt_hk" + sz).collect(Collectors.joining(","));
    }

    @Override
    void initParse(Map<String, Function<String, String[]>> map) {
        map.put("var hq_str_rt_", s -> {
            String[] array = s.replace("var hq_str_rt_", "")
                    .split(",|(=\")");
            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(array[12])).divide(BigDecimal.valueOf(100000), 3, BigDecimal.ROUND_DOWN);
            String[] result = {fun.apply(array[2]), array[0], array[7], array[8], array[9] + "%", "", amount.toString()};
            return result;
        });
    }

}
