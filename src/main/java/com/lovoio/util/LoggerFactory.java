package com.lovoio.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;


/**
 * @author : Roc
 * @date : 2020-12-11 14:53
 * @description :
 **/
public class LoggerFactory {
    private Project project;

    private LoggerFactory() {
    }

    public static LoggerFactory getLogger() {
        return new LoggerFactory();
    }
    private static final Logger LOG = Logger.getInstance(LoggerFactory.class);
    /**
     * 兼容测试
     *
     * @param project 项目
     */
    public void setProject(Project project) {
        this.project = project;
    }

    public void info(String message) {
        if (this.project != null) {
            /* 兼容单元测试  */
            Notifications.Bus.notify(new Notification("ip_log", "IP", message, NotificationType.INFORMATION), this.project);
        }
        LOG.info("---info--IP:"+message);
    }

    public void warn(String message,Throwable throwable) {
        if (this.project != null) {
            /* 兼容单元测试 */
            Notifications.Bus.notify(new Notification("ip_log", "IP", message, NotificationType.WARNING), this.project);
        }
        LOG.warn("---info--IP:"+message,throwable);
    }


    public void debug(String message) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("---debug--IP:"+message);
        }else {
            LOG.info("---debug--IP:"+message);
        }
    }
}
