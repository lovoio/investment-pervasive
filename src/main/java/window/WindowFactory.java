package window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author : Roc
 * @date : 2020-12-08 20:06
 * @description : 窗口工厂，所有数据窗口都在这里创建并添加
 **/
public class WindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        IndexWindow indexWindow = new IndexWindow();
        CnStockWindow cnStockWindow = new CnStockWindow();
        HkStockWindow hkStockWindow = new HkStockWindow();
        UsaStockWindow usaStockWindow = new UsaStockWindow();
        FundWindow fundWindow = new FundWindow();

        Content indexContent = contentFactory.createContent(indexWindow.getContent(), "index", false);
        Content cnContent = contentFactory.createContent(cnStockWindow.getContent(), "cn", false);
        Content hkContent = contentFactory.createContent(hkStockWindow.getContent(), "hk", false);
        Content usaContent = contentFactory.createContent(usaStockWindow.getContent(), "usa", false);
        Content fundContent = contentFactory.createContent(fundWindow.getContent(), "fund", false);
        ContentManager contentManager = toolWindow.getContentManager();

        contentManager.addContent(indexContent);
        contentManager.addContent(cnContent);
        contentManager.addContent(hkContent);
        contentManager.addContent(usaContent);
        contentManager.addContent(fundContent);
    }
}
