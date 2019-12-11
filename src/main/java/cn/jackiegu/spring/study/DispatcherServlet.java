package cn.jackiegu.spring.study;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private static final Log log = LogFactory.get();

    // 配置文件
    private Props props = new Props();

    // 所有的class文件
    private List<String> classNames = new ArrayList<>();

    // IOC容器
    private Map<String, Object> iocMap = new HashMap<>();



    @Override
    public void init(ServletConfig config) {
        loadContentConfig(config);

        scanner(props.getStr("scan.package"));

        instance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    public void destroy() {

    }

    /**
     * 加载上下文配置文件
     *
     * @param config Servlet配置
     */
    private void loadContentConfig(ServletConfig config) {
        log.info("加载上下文配置文件");
        try {
            String contextConfigLocation = config.getInitParameter("contextConfigLocation").replace("classpath:", "");
            ClassPathResource resource = new ClassPathResource(contextConfigLocation);
            props.load(resource.getStream());
        } catch (Exception e) {
            log.error("加载上下文配置文件异常", e);
        }
    }

    /**
     * 扫描所有class文件
     */
    private void scanner(String scanPackage) {
        log.info("开始扫描 scanPackage: {}", scanPackage);
        if (StrUtil.isBlank(scanPackage)) {
            log.error("配置错误 scanPackage: {}", scanPackage);
            return;
        }

        String path = "/" + scanPackage.replaceAll("\\.", "/");
        // 获取扫描路径的URL
        URL url = this.getClass().getClassLoader().getResource(path);
        if (url == null) {
            log.error("路径不存在 url: {} ", path);
            return;
        }

        // 获取扫描路径的File
        File classPath = new File(url.getFile());
        File[] files = classPath.listFiles();
        if (files == null) {
            return;
        }
        // 递归遍历
        for (File file : files) {
            if (file.isDirectory()) {
                this.scanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                classNames.add(scanPackage + "." + file.getName().replace(".class", ""));
            }
        }
    }

    /**
     * 初始化IOC容器
     */
    private void instance() {
        log.info("初始化IOC容器");
        if (classNames.isEmpty()) {
            return;
        }

        try {
            // TODO 遍历所有Class, 并实例化其中带有相关注解的Class然后放入IOC容器中
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                Object object = clazz.newInstance();

            }
        } catch (Exception e) {
            log.error("初始化IOC容器异常", e);
        }
    }






















}
