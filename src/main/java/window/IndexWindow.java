package window;


import entity.dto.IndexStock;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * @author : Roc
 * @date : 2020-12-05 14:15
 * @description :
 **/
public class IndexWindow extends BaseDataWindow {

    private JButton stopButton;
    private JButton startButton;
    private JLabel datetimeLabel;
    private JTable jtable;
    private JScrollPane dataScroll;
    private JPanel dataToolWindowContent;
    /**
     * 实体map对应的表头
     */
    private static final String columnNames = "指数名称,代码,当前,涨跌,涨跌幅,成交量,成交额";

    public IndexWindow() {
        init();
        startButton.addActionListener(e -> {
            startScheduler(columnNames);
            startButton.setEnabled(false);
            stopButton.setEnabled(true);

        });
        stopButton.addActionListener(e -> {
            stopScheduler();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        });
    }

    /**
     * 初始化窗口要更新哪些实体数据
     */
    private void init() {
        jtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        FontMetrics metrics = jtable.getFontMetrics(jtable.getFont());
        jtable.setRowHeight(Math.max(jtable.getRowHeight(), metrics.getHeight()));
        /**
         * 数据对象创建
         */
        IndexStock indexStock = new IndexStock();
        hashMap.put(indexStock.getUri(), indexStock);
    }

    /**
     * 同时推送时间或其他设置
     */
    @Override
    void updateTableView(TableModel tableModel, String time) {
        this.jtable.setModel(tableModel);
        this.datetimeLabel.setText(time);
    }


    @Override
    public JPanel getContent() {
        return this.dataToolWindowContent;
    }


}
