package com.lovoio.dto.entity;

import com.lovoio.config.AppSettingState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author : Roc
 * @date : 2020-12-09 10:55
 * @description :
 **/
public class BaseDataTest  {

    @Before
    public void init(){
        new AppSettingState();
    }

    @Test
    public void testCnDataStock() {

         BaseData baseData = new CnDataStock();
        assertBaseData(baseData);
    }
    @Test
    public void testIndexStock() {
        BaseData baseData = new IndexStock();
        assertBaseData(baseData);
    }
    @Test
    public void testUsaDataStock() {
        BaseData baseData = new UsaDataStock();
        assertBaseData(baseData);
    }
    @Test
    public void testFundStock() {
        BaseData baseData = new FundStock();
        assertBaseData(baseData);
    }


    private void assertBaseData(BaseData baseData) {
        List<String[]> dates = baseData.getData();
        Assert.assertTrue(dates != null && !dates.isEmpty());
        dates.stream().map(Arrays::toString).forEach(System.out::println);
    }
}
