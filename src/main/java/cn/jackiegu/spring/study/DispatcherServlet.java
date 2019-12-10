package cn.jackiegu.spring.study;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {

    private static final Log log = LogFactory.get();

    // 配置文件
    private Props props = new Props();


    @Override
    public void init(ServletConfig config) {
        // 加载配置文件
        loadContentConfig(config);
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

    private void loadContentConfig(ServletConfig config) {
        log.info("开始加载上下文配置文件");
        try {
            String contextConfigLocation = config.getInitParameter("contextConfigLocation").replace("classpath:", "");
            ClassPathResource resource = new ClassPathResource(contextConfigLocation);
            props.load(resource.getStream());
        } catch (Exception e) {
            log.error("加载上下文配置文件异常", e);
        }
    }
}
