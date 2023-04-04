package cn.jackiegu.spring.oneself.framework.webmvc.servlet;

import java.lang.reflect.Method;

/**
 * SpringMVC HandlerMapping组件
 *
 * @author JackieGu
 * @date 2021/3/5
 */
public class HandlerMapping {

    private final String url;

    private final Object controller;

    private final Method method;

    public HandlerMapping(String url, Object controller, Method method) {
        this.url = url;
        this.controller = controller;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public Object getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }
}
