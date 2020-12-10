package entity.dto;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : Roc
 * @date : 2020-12-07 16:17
 * @description : a股市场
 **/
public class CnDataStock extends BaseData {
    private final static String DEFAULT_CODES = "s_sz000895,s_sh600585";


    public CnDataStock() {
        super("https://hq.sinajs.cn/list=");
    }

    @Override
    public void resetCodes() {
        String[] shStockCodes = config.getShStockCodes().split(",");
        String[] szStockCodes = config.getSzStockCodes().split(",");
        Stream<String> shStream = Arrays.stream(shStockCodes).map(sh -> "s_sh" + sh);
        Stream<String> szStream = Arrays.stream(szStockCodes).map(sz -> "s_sz" + sz);
        String collect = Stream.concat(szStream, shStream).collect(Collectors.joining(","));
        this.paramCodes = collect;
    }

    @Override
    void initParse(Map<String, Function<String, String[]>> map) {
        // 已经存在的可以不用重复配置
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
