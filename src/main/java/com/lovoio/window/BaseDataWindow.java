package com.lovoio.window;

import com.lovoio.config.AppSettingState;
import com.lovoio.config.GlobalScheduler;
import com.lovoio.dto.entity.BaseData;
import com.lovoio.util.PinyinUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.time.LocalDateTime;
import java.util.*;
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
    protected ScheduledFuture<?> scheduler;
    protected boolean isSync;

    /**
     * 全局的窗口共享,处理一个窗口被打开多次的情况
     */
    private static Map<Class<? extends BaseDataWindow>, List<BaseDataWindow>> windowsMap = new ConcurrentHashMap<>();

    /**
     * 返回窗口面板
     *
     * @return JPanel
     */
    abstract JPanel getContent();
    /**
     * 获取列明，逗号给开
     *
     * @return String
     */
    abstract String getColumnName();

    /**
     * 更新操作按钮
     * @param isSync 是否同步
     */
    abstract  void updateButtonStatus(boolean isSync);
    /**
     * 更新窗口Table视图
     *
     * @param tableModel 数据表格模型
     * @param time 时间字符串
     */
    abstract void updateTableView(TableModel tableModel, String time);
    /**
     * 统一更新所有窗口对象的配置
     * @param restartScheduler 是否重启定时任务
     */
    public static void updateWindowsConfig(boolean restartScheduler) {
        windowsMap.values().stream().flatMap(Collection::stream).forEach(bdw -> {
            if (restartScheduler && !bdw.isUnattended()){
              bdw.restartScheduler();
            }
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
     * 展示数据
     * 定时任务推送到所有窗口(开启多个idea窗口时)
     * 数据面板不展示则不需要更新
     */
    protected void startScheduler() {
         isSync =true;
        this.scheduler = globalConfig.scheduler(
                this,
                hashMap,
                objArray -> {
                    String time = GlobalScheduler.TIME_FORMATTER.format(LocalDateTime.now());
                    String columnStr = AppSettingState.getInstance().getHiddenMode() ? PinyinUtils.toPinyin(getColumnName()) : getColumnName();
                    DefaultTableModel model = new DefaultTableModel(objArray, columnStr.split(","));
                    List<BaseDataWindow> windows = windowsMap.get(this.getClass());
                    for (BaseDataWindow window : windows) {
                        if ( window.getContent().isShowing() ) {
                            window.updateTableView(model, time);
                            window.updateButtonStatus(isSync);
                        }
                    }
                });
    }

    /**
     * 重启调度任务
     */
    protected void restartScheduler() {
        globalConfig.stopScheduler(this);
        this.startScheduler();
    }

    /**
     * 当无人关注时返回true
     * 只要有一个窗口是同步状态并且是展示的则判定为有人关注
     * @return boolean
     */
    public boolean isUnattended() {
        List<BaseDataWindow> windows = windowsMap.get(this.getClass());
        if (!windows.isEmpty()) {
            for (BaseDataWindow window : windows) {
                if (window.isSync && window.getContent().isShowing()) {
                    return false;
                }
            }
            WindowFactory.logger.info(this.getClass() + "---all Windows are not displayed !");
        }
        this.stopScheduler();
        return true;
    }

    /**
     * 停止同步，并更新所有窗口按钮状态
     */
    void stopScheduler() {
        isSync = false;
        List<BaseDataWindow> windows = windowsMap.get(this.getClass());
        if(!windows.isEmpty()){
            for (BaseDataWindow window : windows) {
                window.updateButtonStatus(isSync);
            }
        }
        globalConfig.stopScheduler(this);
    }


}
