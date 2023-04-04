package cn.jackiegu.spring.oneself.framework.webmvc.servlet;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.jackiegu.spring.oneself.framework.annotation.Controller;
import cn.jackiegu.spring.oneself.framework.annotation.RequestMapping;
import cn.jackiegu.spring.oneself.framework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    private static final long serialVersionUID = 258530496037430365L;

    private static final Log LOGGER = LogFactory.get();

    /**
     * 应用上下文
     */
    private ApplicationContext applicationContext;

    /**
     * URL映射关系集合
     */
    private final List<HandlerMapping> handlerMappings = new ArrayList<>();

    /**
     * 动态参数适配器集合
     */
    private final Map<HandlerMapping, HandlerAdapter> handlerAdapters = new HashMap<>();

    /**
     * 视图解析器
     */
    private ViewResolver viewResolver;

    /**
     * Servlet初始化方法
     *
     * @param config Servlet配置, 包括初始化参数
     */
    @Override
    public void init(ServletConfig config) {
        this.applicationContext = new ApplicationContext(config.getInitParameter("contextConfigLocation"));
        this.initStrategies();
        LOGGER.info("Spring framework is init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HandlerMapping handlerMapping = this.getHandler(req);
        if (handlerMapping == null) {
            this.handleDispatchResult(new ModelAndView("404"), resp);
            return;
        }
        HandlerAdapter handlerAdapter = this.handlerAdapters.get(handlerMapping);
        Object result;
        try {
            result = handlerAdapter.handle(req, resp, handlerMapping);
        } catch (Exception e) {
            LOGGER.error(e);
            this.handleDispatchResult(new ModelAndView("500"), resp);
            return;
        }
        this.handleDispatchResult(result, resp);
    }

    @Override
    public void destroy() {
        LOGGER.trace("Destroy DispatcherServlet instance");
    }

    /**
     * 初始化SpringMVC九大组件
     */
    private void initStrategies() {
        this.initHandlerMappings();
        this.initHandlerAdapters();
        this.initViewResolvers();
    }

    /**
     * 初始化URL映射关系
     */
    private void initHandlerMappings() {
        LOGGER.info("Instantiate handlerMapping");
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
                String url = (baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
                this.handlerMappings.add(new HandlerMapping(url, instance, method));
                LOGGER.info("Mapped: [{}] bind to {}", url, clazz.getName() + "." + method.getName());
            }
        }
    }

    /**
     * 初始化动态参数适配器
     */
    private void initHandlerAdapters() {
        LOGGER.info("Instantiate HandlerAdapter");
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            this.handlerAdapters.put(handlerMapping, new HandlerAdapter());
        }
    }

    /**
     * 初始化视图转换器
     */
    private void initViewResolvers() {
        LOGGER.info("Instantiate ViewResolver");
        String template = this.applicationContext.getConfig().getStr("template");
        try {
            this.viewResolver = new ViewResolver(template);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    /**
     * 获取请求URL映射对象
     */
    private HandlerMapping getHandler(HttpServletRequest request) {
        if (this.handlerMappings.isEmpty()) {
            return null;
        }
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = uri.replace(contextPath, "").replaceAll("/+", "/");
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            if (handlerMapping.getUrl().equals(url)) {
                return handlerMapping;
            }
        }
        return null;
    }

    /**
     * 处理分发结果
     */
    @SuppressWarnings("unchecked")
    private void handleDispatchResult(Object result, HttpServletResponse response) {
        if (result == null) {
            return;
        }
        try {
            this.viewResolver.resolveViewName(result).render(result, response);
        } catch (Exception e) {
            LOGGER.error(e);
            this.handleDispatchResult(ExceptionUtil.stacktraceToString(e), response);
        }
    }
}
