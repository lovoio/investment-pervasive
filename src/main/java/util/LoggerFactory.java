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

    private LoggerFactory(Project project) {
        this.project = project;
    }

    public static LoggerFactory getLogger(Project project) {
        return new LoggerFactory(project);
    }

    public void info(String message) {
        Notifications.Bus.notify(new Notification("ip_log", "IP", message, NotificationType.INFORMATION), this.project);
    }
}
