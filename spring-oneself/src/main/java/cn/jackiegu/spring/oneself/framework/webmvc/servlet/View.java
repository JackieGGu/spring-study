package cn.jackiegu.spring.oneself.framework.webmvc.servlet;

import javax.servlet.http.HttpServletResponse;

/**
 * SpringMVC 视图接口
 *
 * @author JackieGu
 * @date 2021/3/5
 */
public interface View<T> {

    /**
     * 渲染接口返回结果
     *
     * @param result   接口返回结果
     * @param response HTTP响应对象
     */
    void render(T result, HttpServletResponse response) throws Exception;
}
