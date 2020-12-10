package entity.dto;


import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;

/**
 * @author : Roc
 * @date : 2020-12-07 16:17
 * @description : 股票指数对象
 **/
public class IndexStock extends BaseData {


    public IndexStock() {
        super("https://hq.sinajs.cn/list=");
    }


    @Override
    public void resetCodes() {
        this.paramCodes = config.getIndexCodes();
    }

    @Override
    void initParse(Map<String, Function<String, String[]>> map) {

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
        map.put("var hq_str_gb_", s -> {
            String[] array = s.replace("var hq_str_gb_", "")
                    .split(",|(=\")");

            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(array[11])).divide(BigDecimal.valueOf(100000000), 3, BigDecimal.ROUND_DOWN);
            String[] result = {fun.apply(array[1]), array[0], array[2], array[5], array[3] + "%", "", amount.toString()};
            return result;

        });
        map.put("var hq_str_rt_", s -> {
            String[] array = s.replace("var hq_str_rt_", "")
                    .split(",|(=\")");
            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(array[12])).divide(BigDecimal.valueOf(100000), 3, BigDecimal.ROUND_DOWN);
            String[] result = {fun.apply(array[2]), array[0], array[7], array[8], array[9] + "%", "", amount.toString()};
            return result;
        });
        hashMap.put("var hq_str_hf_", s -> {
            String[] dataArray = s.replace("var hq_str_hf_", "")
                    .split(",|(=\")");
            BigDecimal prePrice = new BigDecimal(dataArray[8]);
            BigDecimal nowPrice = new BigDecimal(dataArray[1]);
            BigDecimal subPrice = nowPrice.subtract(prePrice).setScale(2, BigDecimal.ROUND_DOWN);
            BigDecimal rate = subPrice.multiply(BigDecimal.valueOf(100)).divide(prePrice, 2, BigDecimal.ROUND_DOWN);
            String[] data = {fun.apply("小道琼"), "YM", nowPrice.toString(), subPrice.toString(), rate + "%", "", ""};
            return data;
        });
    }

}
