package window;

import entity.dto.CnDataStock;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * @author : Roc
 * @date : 2020-12-09 9:04
 * @description : 港股
 **/
public class CnStockWindow extends BaseDataWindow {
    private JButton stopButton;
    private JButton startButton;
    private JLabel datetimeLabel;
    private JTable jtable;
    private JScrollPane dataScroll;
    private JPanel dataToolWindowContent;
    /**
     * 实体map对应的表头
     */
    private static final String columnNames = "股票名称,代码,当前价,涨跌,涨跌幅,成交量,成交额";

    public CnStockWindow() {
        jtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        FontMetrics metrics = jtable.getFontMetrics(jtable.getFont());
        jtable.setRowHeight(Math.max(jtable.getRowHeight(), metrics.getHeight()));

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
        /**
         * 数据对象创建
         */
        CnDataStock cnDataStock = new CnDataStock();
        hashMap.put(cnDataStock.getUri(), cnDataStock);

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
