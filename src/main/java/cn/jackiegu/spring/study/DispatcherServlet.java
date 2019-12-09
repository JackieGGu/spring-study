package cn.jackiegu.spring.study;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class DispatcherServlet extends HttpServlet {

    private static final Log log = LogFactory.get();

    // 配置文件
    private Props props = new Props();


    @Override
    public void init(ServletConfig config) {
        // 加载配置文件
        loadContentConfig(config);
    }

    private void loadContentConfig(ServletConfig config) {
        log.info("加载上下文配置文件");
        try {
            String contextConfigLocation = config.getInitParameter("contextConfigLocation").replace("classpath:", "");
            InputStream is = DispatcherServlet.class.getClassLoader().getResourceAsStream(contextConfigLocation);
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void destroy() {

    }
}
