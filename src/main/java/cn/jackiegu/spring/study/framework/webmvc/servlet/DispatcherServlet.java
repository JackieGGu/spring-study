package cn.jackiegu.spring.study.framework.webmvc.servlet;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.jackiegu.spring.study.framework.annotation.Controller;
import cn.jackiegu.spring.study.framework.annotation.RequestMapping;
import cn.jackiegu.spring.study.framework.annotation.RequestParam;
import cn.jackiegu.spring.study.framework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
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
     * 应用上下文
     */
    private ApplicationContext applicationContext;

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
        this.applicationContext = new ApplicationContext(config.getInitParameter("contextConfigLocation"));
        this.initHandlerMapping();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }

    @SuppressWarnings("unchecked")
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
        if (this.applicationContext.getBeanDefinitionCount() == 0) {
            return;
        }
        String[] beanDefinitionNames = this.applicationContext.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            Object instance = this.applicationContext.getBean(beanName);
            Class<?> clazz = instance.getClass();
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
                Handler handler = new Handler(instance, method);
                this.handlerMapping.put(url, handler);
                LOGGER.info("Mapped: [{}] bind {}", url, clazz.getName() + "." + method.getName());
            }
        }
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
