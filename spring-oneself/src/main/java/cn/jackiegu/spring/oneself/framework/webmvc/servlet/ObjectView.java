package cn.jackiegu.spring.oneself.framework.webmvc.servlet;

import cn.hutool.json.JSONUtil;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 对象视图
 *
 * @author JackieGu
 * @date 2021/3/8
 */
public class ObjectView implements View<Object> {

    @Override
    public void render(Object result, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }
}
