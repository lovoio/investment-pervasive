package com.lovoio.dto.entity;


import com.lovoio.dto.IDataTransformSina;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : Roc
 * @date : 2020-12-07 16:17
 * @description : a股市场
 **/
public class HkDataStock extends BaseData implements IDataTransformSina {

    public HkDataStock() {
        super(DATA_URL);
    }


    @Override
    public void resetCodes() {
        String[] hkStockCodes = config.getHkStockCodes().split(",");
        this.paramCodes = Arrays.stream(hkStockCodes).map(sz -> "rt_hk" + sz).collect(Collectors.joining(","));
    }

    @Override
    void initParse(Map<String, Function<String, String[]>> map) {
        map.put("var hq_str_rt_",HK_TSF);
    }

}
