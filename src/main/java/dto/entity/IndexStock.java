package dto.entity;


import dto.IDataTransformSina;

import java.util.Map;
import java.util.function.Function;

/**
 * @author : Roc
 * @date : 2020-12-07 16:17
 * @description : 股票指数对象
 **/
public class IndexStock extends BaseData implements IDataTransformSina {


    public IndexStock() {
        super(DATA_URL);
    }


    @Override
    public void resetCodes() {
        this.paramCodes = config.getIndexCodes();
    }

    @Override
    void initParse(Map<String, Function<String, String[]>> map) {

        map.put("var hq_str_s_", CN_INDEX_TSF);
        map.put("var hq_str_gb_", USA_INDEX_TSF);
        map.put("var hq_str_rt_",HK_INDEX_TSF);
        map.put("var hq_str_hf_", FUTURE_TSF);
    }

}
