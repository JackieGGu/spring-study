package cn.jackiegu.spring.oneself.framework.webmvc.servlet;

import java.util.Map;

/**
 * SpringMVC ModelAndView
 *
 * @author JackieGu
 * @date 2021/3/5
 */
public class ModelAndView {

    private final String viewName;

    private Map<String, ?> model;

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public ModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }
}
