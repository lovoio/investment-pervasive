package entity.dto;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : Roc
 * @date : 2020-12-07 16:17
 * @description : 基金
 **/
public class FundStock extends BaseData {
    public FundStock() {
        super("https://hq.sinajs.cn/list=");
    }

    @Override
    public void resetCodes() {
        String[] fundStockCodes = config.getFundCodes().split(",");
        this.paramCodes = Arrays.stream(fundStockCodes).map(sz -> "s_" + sz).collect(Collectors.joining(","));
    }

    @Override
    void initParse(Map<String, Function<String, String[]>> map) {
        //指数窗口实体中已经设置
        map.put("var hq_str_s_", s -> {
            String[] array = s.replace("var hq_str_s_", "")
                    .split(",|(=\")");
            BigDecimal VOL = new BigDecimal(array[5]).divide(BigDecimal.valueOf(1000000), 3, BigDecimal.ROUND_DOWN);
            BigDecimal amount = new BigDecimal(array[6]).divide(BigDecimal.valueOf(10000), 3, BigDecimal.ROUND_DOWN);
            String temp = array[0];
            array[0] = fun.apply(array[1]);
            array[1] = temp;
            array[4] = array[4] + "%";
            array[5] = VOL.toString();
            array[6] = amount.toString();
            return array;
        });
    }


}
