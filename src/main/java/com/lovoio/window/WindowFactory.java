package com.lovoio.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.lovoio.dto.entity.*;
import org.jetbrains.annotations.NotNull;
import com.lovoio.util.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author : Roc
 * @date : 2020-12-08 20:06
 * @description : 窗口工厂，所有数据窗口都在这里创建并添加
 **/
public class WindowFactory implements ToolWindowFactory {
    public static LoggerFactory logger = LoggerFactory.getLogger();

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      logger.setProject(project);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        IndexWindow indexWindow = new IndexWindow(Collections.singletonList(new IndexStock()));
        CnStockWindow cnStockWindow = new CnStockWindow(Collections.singletonList(new CnDataStock()));
        HkStockWindow hkStockWindow = new HkStockWindow(Collections.singletonList(new HkDataStock()));
        UsaStockWindow usaStockWindow = new UsaStockWindow(Collections.singletonList(new UsaDataStock()));
        FundWindow fundWindow = new FundWindow(Collections.singletonList(new FundStock()));

        Content indexContent = contentFactory.createContent(indexWindow.getContent(), "Index", false);
        Content cnContent = contentFactory.createContent(cnStockWindow.getContent(), "CN", false);
        Content hkContent = contentFactory.createContent(hkStockWindow.getContent(), "HK", false);
        Content usaContent = contentFactory.createContent(usaStockWindow.getContent(), "US", false);
        Content fundContent = contentFactory.createContent(fundWindow.getContent(), "FUND", false);
        ContentManager contentManager = toolWindow.getContentManager();

        contentManager.addContent(indexContent);
        contentManager.addContent(cnContent);
        contentManager.addContent(hkContent);
        contentManager.addContent(usaContent);
        contentManager.addContent(fundContent);
    }
}
