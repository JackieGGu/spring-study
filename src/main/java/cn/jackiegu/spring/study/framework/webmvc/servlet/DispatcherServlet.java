package cn.jackiegu.spring.study.framework.webmvc.servlet;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;
import cn.jackiegu.spring.study.framework.annotation.Autowired;
import cn.jackiegu.spring.study.framework.annotation.Controller;
import cn.jackiegu.spring.study.framework.annotation.RequestMapping;
import cn.jackiegu.spring.study.framework.annotation.RequestParam;
import cn.jackiegu.spring.study.framework.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SpringMVC核心类
 *
 * @author JackieGu
 * @date 2020/1/20
 */
public class DispatcherServlet extends HttpServlet {

    private static final Log LOGGER = LogFactory.get();

    /**
     * 配置文件
     */
    private final Props props = new Props();

    /**
     * 所有的class文件
     */
    private final List<String> classNames = new ArrayList<>();

    /**
     * IOC容器
     */
    private final Map<String, Object> iocMap = new HashMap<>();

    /**
     * handlerMapping容器
     */
    private final Map<String, Handler> handlerMapping = new HashMap<>();

    /**
     * Servlet初始化方法
     *
     * @param config Servlet配置, 包括初始化参数
     */
    @Override
    public void init(ServletConfig config) {
        this.loadContentConfig(config);
        String scanPackage = this.props.getStr("scan.package");
        this.scanner(scanPackage);
        this.instance();
        this.autowired();
        this.initHandlerMapping();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.replace(contextPath, "").replaceAll("/+", "/");
        if (!this.handlerMapping.containsKey(path)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("404 Not Found");
            return;
        }
        Handler handler = this.handlerMapping.get(path);
        Class<?>[] parameterTypes = handler.method.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];
        Map<String, Integer> parameterSort = handler.parameterSort;
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Map.Entry<String, String[]> item : parameterMap.entrySet()) {
            String key = item.getKey();
            if (!parameterSort.containsKey(key)) {
                continue;
            }
            int index = parameterSort.get(key);
            args[index] = this.convert(parameterTypes[index], item.getValue());
        }
        if (parameterSort.containsKey(HttpServletRequest.class.getName())) {
            int index = parameterSort.get(HttpServletRequest.class.getName());
            args[index] = req;
        }
        if (parameterSort.containsKey(HttpServletResponse.class.getName())) {
            int index = parameterSort.get(HttpServletResponse.class.getName());
            args[index] = resp;
        }
        try {
            Object o = handler.method.invoke(handler.controller, args);
            resp.setContentType("text/plain;charset=utf-8");
            if (o instanceof String) {
                resp.getWriter().write((String) o);
            } else {
                resp.getWriter().write(JSONUtil.parse(o).toString());
            }
        } catch (Exception e) {
            LOGGER.error(e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("500 Exception, Detail: " + Arrays.toString(e.getStackTrace()));
        }
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
        LOGGER.info("加载上下文配置文件");
        try {
            String contextConfigLocation = config.getInitParameter("contextConfigLocation").replace("classpath:", "");
            ClassPathResource resource = new ClassPathResource(contextConfigLocation);
            this.props.load(resource.getStream());
        } catch (Exception e) {
            LOGGER.error("加载上下文配置文件异常", e);
        }
    }

    /**
     * 扫描所有class文件
     *
     * @param scanPackage 扫描的包
     */
    private void scanner(String scanPackage) {
        LOGGER.info("开始扫描 scanPackage: {}", scanPackage);
        if (StrUtil.isBlank(scanPackage)) {
            LOGGER.error("配置错误 scanPackage: {}", scanPackage);
            return;
        }

        String path = "/" + scanPackage.replaceAll("\\.", "/");
        // 获取扫描路径的URL
        URL url = this.getClass().getClassLoader().getResource(path);
        if (url == null) {
            LOGGER.error("路径不存在 url: {} ", path);
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
                this.classNames.add(scanPackage + "." + file.getName().replace(".class", ""));
            }
        }
    }

    /**
     * 初始化IOC容器
     */
    private void instance() {
        LOGGER.info("初始化IOC容器");
        if (this.classNames.isEmpty()) {
            return;
        }
        try {
            for (String className : this.classNames) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Controller.class)) {
                    String beanName = this.toLowerFirstCase(clazz.getSimpleName());
                    Controller controller = clazz.getAnnotation(Controller.class);
                    if (!"".equals(controller.value())) {
                        beanName = controller.value();
                    }
                    Object instance = clazz.newInstance();
                    this.iocMap.put(beanName, instance);
                }
                if (clazz.isAnnotationPresent(Service.class)) {
                    String beanName = this.toLowerFirstCase(clazz.getSimpleName());
                    Service service = clazz.getAnnotation(Service.class);
                    if (!"".equals(service.value())) {
                        beanName = service.value();
                    }
                    Object instance = clazz.newInstance();
                    this.iocMap.put(beanName, instance);

                    for (Class<?> i : clazz.getInterfaces()) {
                        if (this.iocMap.containsKey(i.getName())) {
                            throw new Exception("the beanName:" + i.getName() + " is exists!");
                        }
                        // 接口beanName规则:包名+类名
                        this.iocMap.put(i.getName(), instance);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("初始化IOC容器异常", e);
        }
    }

    /**
     * 自动依赖注入
     */
    private void autowired() {
        LOGGER.info("自动依赖注入");
        if (this.iocMap.isEmpty()) {
            return;
        }
        try {
            for (Map.Entry<String, Object> entry : this.iocMap.entrySet()) {
                Field[] fields = entry.getValue().getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(Autowired.class)) {
                        continue;
                    }
                    String beanName = field.getType().getName();
                    field.setAccessible(true);
                    field.set(entry.getValue(), this.iocMap.get(beanName));
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("自动依赖注入", e);
        }
    }

    /**
     * 类型转换
     *
     * @param type  转换类型
     * @param value 被转换值
     * @return 转换后值
     */
    private Object convert(Class<?> type, String[] value) {
        if (ClassUtil.isBasicType(type) || type == String.class) {
            return Convert.convert(type, value[0]);
        } else if (type.isArray()) {
            Class<?> componentType = type.getComponentType();
            Object result = Array.newInstance(componentType, value.length);
            for (int i = 0; i < value.length; i++) {
                Array.set(result, i, Convert.convert(componentType, value[i]));
            }
            return result;
        } else {
            return null;
        }
    }

    /**
     * 初始化HandlerMapping
     */
    private void initHandlerMapping() {
        LOGGER.info("初始化HandlerMapping");
        if (this.iocMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : this.iocMap.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(Controller.class)) {
                continue;
            }
            StringBuilder baseUrl = new StringBuilder();
            baseUrl.append("/");
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                baseUrl.append(requestMapping.value());
            }
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String url = (baseUrl.toString() + "/" + requestMapping.value()).replaceAll("/+", "/");
                Handler handler = new Handler(entry.getValue(), method);
                this.handlerMapping.put(url, handler);
                LOGGER.info("Mapped: [{}] onto {}", url, clazz.getName() + "." + method.getName());
            }
        }
    }

    /**
     * 将类名首字母小写
     *
     * @param simpleName 类名
     * @return 首字母小写的类名
     */
    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }

    /**
     * 内置处理器
     */
    private static class Handler {

        Object controller;

        Method method;

        Map<String, Integer> parameterSort;

        Handler(Object controller, Method method) {
            this.controller = controller;
            this.method = method;
            this.parameterSort = new HashMap<>();
            this.parseParameterSort(method);
        }

        /**
         * 解析方法参数顺序
         * SpringMVC可通过方法参数名去匹配参数, 采用的是ASM去解析class字节码文件中的LocalVariableTable信息
         *
         * @param method 方法
         */
        private void parseParameterSort(Method method) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (Annotation annotation : parameterAnnotations[i]) {
                    if (annotation instanceof RequestParam) {
                        String parameterName = ((RequestParam) annotation).value();
                        if (StrUtil.isNotBlank(parameterName)) {
                            parameterSort.put(parameterName, i);
                        }
                    }
                }
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> type = parameterTypes[i];
                if (HttpServletRequest.class == type || HttpServletResponse.class == type) {
                    parameterSort.put(type.getName(), i);
                }
            }
        }
    }
}
