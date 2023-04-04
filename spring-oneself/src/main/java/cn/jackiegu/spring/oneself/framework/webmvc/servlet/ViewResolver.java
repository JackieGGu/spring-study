package cn.jackiegu.spring.oneself.framework.webmvc.servlet;

import java.io.File;
import java.net.URL;

/**
 * SpringMVC ViewResolver组件
 *
 * @author JackieGu
 * @date 2021/3/5
 */
public class ViewResolver {

    private static final String DEFAULT_TEMPLATE_SUFFIX = ".html";

    private final File templateDir;

    public ViewResolver(String template) throws Exception {
        URL resource = this.getClass().getClassLoader().getResource(template);
        if (resource == null) {
            throw new Exception("The template file directory does not exist");
        }
        String templatePath = resource.getPath();
        this.templateDir = new File(templatePath);
    }

    @SuppressWarnings("rawtypes")
    public View resolveViewName(Object result) {
        if (result instanceof ModelAndView) {
            ModelAndView mv = (ModelAndView) result;
            String viewName = mv.getViewName();
            if (!viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX)) {
                viewName = viewName + DEFAULT_TEMPLATE_SUFFIX;
            }
            String pathname = (templateDir.getPath() + "/" + viewName).replaceAll("/+", "/");
            File viewFile = new File(pathname);
            return new TemplateView(viewFile);
        } else if (result instanceof String) {
            return new StringView();
        } else {
            return new ObjectView();
        }
    }
}
