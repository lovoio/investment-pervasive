package com.lovoio.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.lovoio.window.WindowFactory;

/**
 * @author : Roc
 * @date : 2020-12-10 13:58
 * @description : 引用设置持久化
 **/
@State(name = "com.lovoio.config.AppSettingState", storages = {@Storage("ip_plugin_setting.xml")})
public class AppSettingState implements PersistentStateComponent<AppSettingState> {

    private static final Boolean IS_HIDDEN_MODE = false;

    private static final String INDEX_CODES = "s_sh000001,s_sh000300,s_sz399001,rt_hkHSI,gbDji,gb_ixic,gb_inx,hf_YM";

    private static final String SH_STOCK_CODES = "600585";

    private static final String SZ_STOCK_CODES = "000895";

    private static final String HK_STOCK_CODES = "00700";

    private static final String USA_STOCK_CODES = "aapl";

    private static final String FUND_CODES = "sz159983";


    private Boolean isHiddenMode = IS_HIDDEN_MODE;

    private String indexCodes = INDEX_CODES;

    private String shStockCodes = SH_STOCK_CODES;

    private String szStockCodes = SZ_STOCK_CODES;

    private String hkStockCodes = HK_STOCK_CODES;

    private String usaStockCodes = USA_STOCK_CODES;

    private String fundCodes = FUND_CODES;
    /**
     * 延迟执行时间（秒）
     */
    long delay = 3;
    /**
     * 执行的时间间隔（秒）
     */
    private long period = 3;

    private static AppSettingState INSTANCE = null;

    public AppSettingState() {
        INSTANCE = this;
    }

    public static AppSettingState getInstance() {
        try {
            AppSettingState service = ServiceManager.getService(AppSettingState.class);
            if (service != null) {
                INSTANCE = service;
            }
        } catch (Exception e) {
            WindowFactory.logger.info("get setting error:" + e.getMessage());
        }
        return INSTANCE;
    }

    public Boolean getHiddenMode() {
        return isHiddenMode;
    }

    public void setHiddenMode(Boolean hiddenMode) {
        this.isHiddenMode = hiddenMode;
    }

    public String getIndexCodes() {
        return indexCodes;
    }

    public void setIndexCodes(String indexCodes) {
        this.indexCodes = indexCodes;
    }

    public String getShStockCodes() {
        return shStockCodes;
    }

    public void setShStockCodes(String shStockCodes) {
        this.shStockCodes = shStockCodes;
    }

    public String getSzStockCodes() {
        return szStockCodes;
    }

    public void setSzStockCodes(String szStockCodes) {
        this.szStockCodes = szStockCodes;
    }

    public String getHkStockCodes() {
        return hkStockCodes;
    }

    public void setHkStockCodes(String hkStockCodes) {
        this.hkStockCodes = hkStockCodes;
    }

    public String getUsaStockCodes() {
        return usaStockCodes;
    }

    public void setUsaStockCodes(String usaStockCodes) {
        this.usaStockCodes = usaStockCodes;
    }

    public String getFundCodes() {
        return fundCodes;
    }

    public void setFundCodes(String fundCodes) {
        this.fundCodes = fundCodes;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    @Nullable
    @Override
    public AppSettingState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    /**
     * 重置
     */
    public void reset() {
        setHiddenMode(IS_HIDDEN_MODE);
        setIndexCodes(INDEX_CODES);
        setShStockCodes(SH_STOCK_CODES);
        setSzStockCodes(SZ_STOCK_CODES);
        setHkStockCodes(HK_STOCK_CODES);
        setUsaStockCodes(USA_STOCK_CODES);
        setFundCodes(FUND_CODES);
    }
}
