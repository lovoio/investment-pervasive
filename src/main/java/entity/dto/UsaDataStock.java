package entity.dto;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : Roc
 * @date : 2020-12-07 16:17
 * @description : 美股数据对象
 **/
public class UsaDataStock extends BaseData {
    public UsaDataStock() {
        super("https://hq.sinajs.cn/list=");
    }

    @Override
    public void resetCodes() {
        String[] usaStockCodes = config.getUsaStockCodes().split(",");
        this.paramCodes = Arrays.stream(usaStockCodes).map(sz -> "gb_" + sz).collect(Collectors.joining(","));
    }

    @Override
    void initParse(Map<String, Function<String, String[]>> map) {
        //指数窗口实体中已经设置
        map.put("var hq_str_gb_", s -> {

            String[] array = s.replace("var hq_str_gb_", "")
                    .split(",|(=\")");

            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(array[11])).divide(BigDecimal.valueOf(100000000), 3, BigDecimal.ROUND_DOWN);
            String[] result = {fun.apply(array[1]), array[0], array[2], array[5], array[3] + "%", "", amount.toString()};
            return result;

        });
    }


    public static void main(String[] args) {
        UsaDataStock indexStock = new UsaDataStock();
        List<String[]> dates = indexStock.getData();
        dates.stream().map(a -> Arrays.toString(a)).forEach(System.out::println);
    }


}
