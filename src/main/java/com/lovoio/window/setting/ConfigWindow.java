package com.lovoio.window.setting;

import com.intellij.openapi.options.Configurable;
import com.lovoio.config.AppSettingState;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import com.lovoio.window.BaseDataWindow;
import com.lovoio.window.WindowFactory;

import javax.swing.*;
import java.awt.event.ItemEvent;

/**
 * @author : Roc
 * @date : 2020-12-08 14:21
 * @description :
 **/
public class ConfigWindow implements Configurable {
    private AppSettingState config = AppSettingState.getInstance();
    private JPanel globalConfig;
    /**
     * 隐秘模式
     */
    private JCheckBox isHideMode;
    private JLabel hideModeDesc;
    private JLabel indexLabel;
    private JTextField indexCodes;

    private JLabel shStockLabel;
    private JTextField shStockCodes;

    private JLabel szStockLabel;
    private JTextField szStockCodes;

    private JLabel hkStockLabel;
    private JTextField hkStockCodes;

    private JLabel usaStockLabel;
    private JTextField usaStockCodes;

    private JLabel fundLabel;
    private JTextField fundCodes;

    private JLabel periodLabel;

    private JTextField period;

    public ConfigWindow() {
    }


    @Override
    @Nls(capitalization = Nls.Capitalization.Title)
    public String getDisplayName() {
        return globalConfig.getToolTipText();
    }

    @Override
    public @Nullable JComponent createComponent() {
        initComponentText();

        isHideMode.addItemListener(l -> {
            boolean hidden = l.getStateChange() == ItemEvent.SELECTED;
        });

        return globalConfig;
    }

    /**
     * 初始化组件值
     */
    private void initComponentText() {
        isHideMode.setSelected(config.getHiddenMode());
        indexCodes.setText(config.getIndexCodes());
        fundCodes.setText(config.getFundCodes());
        hkStockCodes.setText(config.getHkStockCodes());
        shStockCodes.setText(config.getShStockCodes());
        szStockCodes.setText(config.getSzStockCodes());
        usaStockCodes.setText(config.getUsaStockCodes());
        fundCodes.setText(config.getFundCodes());
        period.setText(String.valueOf(config.getPeriod()));
    }

    @Override
    public void apply() {
        config.setHiddenMode(isHideMode.isSelected());
        config.setFundCodes(fundCodes.getText());
        config.setHkStockCodes(hkStockCodes.getText());
        config.setIndexCodes(indexCodes.getText());
        /*刷新频率需改需要先结束原先的定时任务*/
        long oldPeriod = config.getPeriod();
        long nowPeriod = Long.parseLong(period.getText());
        config.setPeriod(nowPeriod);
        config.setShStockCodes(shStockCodes.getText());
        config.setSzStockCodes(szStockCodes.getText());
        config.setUsaStockCodes(usaStockCodes.getText());
        config.setFundCodes(fundCodes.getText());
        /*通知所有数据窗口更新*/
        BaseDataWindow.updateWindowsConfig(nowPeriod != oldPeriod);
        WindowFactory.logger.info("setting updated !");
    }


    @Override
    public boolean isModified() {
        return true;
    }


    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }


    @Override
    public void reset() {
    }



    @Override
    public void cancel() {

    }
}
