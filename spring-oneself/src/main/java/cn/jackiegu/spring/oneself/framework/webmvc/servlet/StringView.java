package cn.jackiegu.spring.oneself.framework.webmvc.servlet;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 文本视图
 *
 * @author JackieGu
 * @date 2021/3/8
 */
public class StringView implements View<String> {

    @Override
    public void render(String result, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain;charset=UTF-8");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(result);
    }
}
