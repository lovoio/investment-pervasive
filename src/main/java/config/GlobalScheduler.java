package config;

import entity.dto.BaseData;
import window.BaseDataWindow;
import window.WindowFactory;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author : Roc
 * @date : 2020-12-08 15:12
 * @description : 全局配置
 **/
public enum GlobalScheduler {
    /**
     * 全局单例
     */
    INSTANCE;

    private AppSettingState appSetting = AppSettingState.getInstance();
    /**
     * 时间格式化
     */
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 所有窗口被定时器统一刷新，而不是每个窗口都开定时任务去取数据
     */
    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1, new DiscardOldestPolicy());
    /**
     * 同类窗口统一处理
     */
    Map<Class, ScheduledFuture<?>> windowScheduleMap = new HashMap<>();


    /**
     * 停止定时任务
     *
     * @param window
     */
    public void stopScheduler(BaseDataWindow window) {
        Class<? extends BaseDataWindow> windowClass = window.getClass();
        WindowFactory.logger.info(windowClass.getSimpleName() + "定时任务准备关闭");
        ScheduledFuture<?> future = windowScheduleMap.get(windowClass);
        if (future != null) {
            future.cancel(false);
        }
    }

    /**
     * 开启调度任务
     *
     * @param window   要被更新的窗口
     * @param hashMap  要去获取处理数据的对象
     * @param consumer 数据处理后被消费到各个窗口
     * @return
     */
    public ScheduledFuture scheduler(BaseDataWindow window, HashMap<String, BaseData> hashMap, Consumer<Object[][]> consumer) {
        Class<? extends BaseDataWindow> clazz = window.getClass();
        ScheduledFuture<?> future = windowScheduleMap.get(clazz);
        /**
         * 定时任务存在并未结束则直接返回
         */
        if (future != null && !future.isCancelled()) {
            return future;
        }
        ScheduledFuture<?> scheduledFuture = executorService.scheduleAtFixedRate(() -> {
            try {
                /**
                 * 所有窗口关闭时停止网络请求
                 */
                if (window.isUnattended()) {
                    return;
                }
                Collection<BaseData> values = hashMap.values();
                if (values == null || values.isEmpty()) {
                    return;
                }
                List<String[]> indices = new ArrayList<>();
                for (BaseData bIndex : values) {
                    List<String[]> data = bIndex.getData();
                    if (data != null) {
                        indices.addAll(data);
                    }
                }
                Object[][] array = indices.toArray(new Object[][]{});
                consumer.accept(array);
            } catch (Exception e) {
                WindowFactory.logger.info(clazz.getSimpleName() + "定时任务异常：" + e.getMessage());
            }
        }, appSetting.delay, appSetting.getPeriod(), TimeUnit.SECONDS);
        windowScheduleMap.put(clazz, scheduledFuture);
        WindowFactory.logger.info(clazz.getSimpleName() + "定时任务已创建！");
        return scheduledFuture;
    }


}
