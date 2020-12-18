package com.lovoio.window;

import com.lovoio.dto.entity.BaseData;
import com.lovoio.dto.entity.HkDataStock;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

/**
 * @author : Roc
 * @date : 2020-12-09 9:04
 * @description : 港股
 **/
public class HkStockWindow extends BaseDataWindow {
    private JButton stopButton;
    private JButton startButton;
    private JLabel datetimeLabel;
    private JTable jtable;
    private JScrollPane dataScroll;
    private JPanel dataToolWindowContent;

    /**
     * 实体map对应的表头
     */
    @Override
    String getColumnName() {
        return "股票名称,代码,当前价,涨跌,涨跌幅,成交量,成交额";
    }
    @Override
    void updateButtonStatus(boolean isSync) {
        if (isSync){
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }else {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }
    public HkStockWindow(List<BaseData> baseDataList) {
        super(baseDataList);
        jtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        FontMetrics metrics = jtable.getFontMetrics(jtable.getFont());
        jtable.setRowHeight(Math.max(jtable.getRowHeight(), metrics.getHeight()));

        startButton.addActionListener(e -> {
            startScheduler();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);

        });
        stopButton.addActionListener(e -> {
            stopScheduler();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        });
        HkDataStock hkDataStock = new HkDataStock();
        hashMap.put(hkDataStock.getUri(), hkDataStock);

    }

    @Override
    void updateTableView(TableModel tableModel, String time) {
        this.jtable.setModel(tableModel);
        this.datetimeLabel.setText(time);
    }

    @Override
    JPanel getContent() {
        return this.dataToolWindowContent;
    }

}
