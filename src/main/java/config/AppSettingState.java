package config;

import com.intellij.ide.impl.ProjectUtil;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import window.WindowFactory;

import java.net.URLDecoder;

/**
 * @author : Roc
 * @date : 2020-12-10 13:58
 * @description : 引用设置持久化
 **/
@State(name = "config.AppSettingState", storages = {@Storage("ip_plugin_setting.xml")})
public class AppSettingState implements PersistentStateComponent<AppSettingState> {

    private static final Boolean IsHiddenModeDefault = false;

    private static final String IndexCodesDefault = "s_sh000001,s_sh000300,s_sz399001,rt_hkHSI,gbDji,gb_ixic,gb_inx,hf_YM";

    private static final String ShStockCodesDefault = "600585";

    private static final String SzStockCodesDefault = "000895";

    private static final String HkStockCodesDefault = "00700";

    private static final String UsaStockCodesDefault = "aapl";

    private static final String FundCodesDefault = "sz159983";


    private Boolean isHiddenMode = IsHiddenModeDefault;

    private String indexCodes = IndexCodesDefault;

    private String shStockCodes = ShStockCodesDefault;

    private String szStockCodes = SzStockCodesDefault;

    private String hkStockCodes = HkStockCodesDefault;

    private String usaStockCodes = UsaStockCodesDefault;

    private String fundCodes = FundCodesDefault;
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
        /**
         * 兼容测试
         */
        try {
            AppSettingState service = ServiceManager.getService(AppSettingState.class);
            if (service != null) {
                INSTANCE = service;
            }
        } catch (Exception e) {
            WindowFactory.logger.info("获取持久化配置异常:" + e.getMessage());
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
        setHiddenMode(IsHiddenModeDefault);
        setIndexCodes(IndexCodesDefault);
        setShStockCodes(ShStockCodesDefault);
        setSzStockCodes(SzStockCodesDefault);
        setHkStockCodes(HkStockCodesDefault);
        setUsaStockCodes(UsaStockCodesDefault);
        setFundCodes(FundCodesDefault);
    }
}
