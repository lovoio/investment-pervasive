package window;

import config.AppSettingState;
import config.GlobalScheduler;
import entity.dto.BaseData;
import util.PinyinUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author : Roc
 * @date : 2020-12-08 16:49
 * @description : 基础的数据展示窗口
 **/
public abstract class BaseDataWindow {

    private GlobalScheduler globalConfig = GlobalScheduler.INSTANCE;
    /**
     * 窗口的定时任务
     */
    protected ScheduledFuture scheduler;

    /**
     * 全局的窗口共享,处理一个窗口被打开多次的情况
     */
    private static Map<Class<? extends BaseDataWindow>, List<BaseDataWindow>> windowsMap = new ConcurrentHashMap<>();

    /**
     * 统一更新所有窗口对象的配置
     */
    public static void updateWindowsConfig() {
        windowsMap.values().stream().flatMap(l -> l.stream()).forEach(bdw -> {
            for (BaseData value : bdw.hashMap.values()) {
                value.resetCodes();
            }
        });
    }

    /**
     * 执行数据更新的对象实体map
     */
    HashMap<String, BaseData> hashMap = new HashMap<>(8);

    public BaseDataWindow() {
        List<BaseDataWindow> windows = windowsMap.get(this.getClass());
        if (windows == null) {
            windows = new ArrayList<>();
            windows.add(this);
            windowsMap.put(this.getClass(), windows);
        } else {
            windows.add(this);
        }
    }

    /**
     * 更新窗口Table视图
     *
     * @param tableModel
     * @param time
     */
    abstract void updateTableView(TableModel tableModel, String time);

    /**
     * 展示数据
     *
     * @param columnNames 数据窗口表头
     */
    protected void startScheduler(String columnNames) {
        this.scheduler = globalConfig.scheduler(
                this,
                hashMap,
                objArray -> {
                    /**
                     * 定时任务推送到所有窗口(开启多个idea窗口时)
                     */
                    String time = GlobalScheduler.TIME_FORMATTER.format(LocalDateTime.now());
                    String columnStr = AppSettingState.getInstance().getHiddenMode() ? PinyinUtils.toPinyin(columnNames) : columnNames;
                    DefaultTableModel model = new DefaultTableModel(objArray, columnStr.split(","));
                    List<BaseDataWindow> windows = windowsMap.get(this.getClass());
                    for (BaseDataWindow window : windows) {
                        /**
                         * 数据面板不展示则不需要更新
                         */
                        if (window.getContent().isShowing()) {
                            window.updateTableView(model, time);
                        }
                    }
                });
    }

    /**
     * 当无人关注时返回true
     *
     * @return
     */
    public boolean isUnattended() {
        List<BaseDataWindow> windows = windowsMap.get(this.getClass());
        if (windows.isEmpty()) {
            return true;
        }
        for (BaseDataWindow window : windows) {
            if (window.getContent().isShowing()) {
                return false;
            }
        }
        return true;
    }


    void stopScheduler() {
        globalConfig.stopScheduler(this);
    }

    /**
     * 返回窗口面板
     *
     * @return JPanel
     */
    abstract JPanel getContent();
}
