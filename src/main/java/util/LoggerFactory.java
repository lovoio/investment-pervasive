package util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
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

    /**
     * 兼容测试
     *
     * @param project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    public void info(String message) {
        if (this.project != null) {
            /**
             * 兼容单元测试
             */
            Notifications.Bus.notify(new Notification("ip_log", "IP", message, NotificationType.INFORMATION), this.project);
        }
    }
}
