package cn.jackiegu.spring.oneself.framework.aop.support;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.jackiegu.spring.oneself.framework.aop.aspect.AdviceAspect;
import cn.jackiegu.spring.oneself.framework.aop.config.AopConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AOP通知支持类型
 *
 * @author JackieGu
 * @date 2021/3/11
 */
public class AdviceSupport {

    private static final Log LOGGER = LogFactory.get();

    private final AopConfig config;

    private Object target;

    private Class<?> targetClass;

    private Pattern pointcutClassPattern;

    private Map<Method, Map<String, AdviceAspect>> methodCache;

    public AdviceSupport(AopConfig config) {
        this.config = config;
    }

    public Map<String, AdviceAspect> getAdvices(Method method) {
        return this.methodCache.get(method);
    }

    /**
     * 匹配目标Class是否为被切Class
     */
    public boolean pointcutMatch() {
        return this.pointcutClassPattern.matcher(this.targetClass.toString()).matches();
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getTarget() {
        return target;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        this.parseClass();
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    /**
     * 解析Class, 匹配方法
     */
    @SuppressWarnings("all")
    private void parseClass() {
        // 切点Class正则表达式
        String pointcut = this.config.getPointcut();
        String pointcutClass = pointcut.substring(0, pointcut.lastIndexOf("."));
        this.pointcutClassPattern = Pattern.compile("class " + pointcutClass.replaceAll("\\.", "\\\\."));

        try {
            this.methodCache = new HashMap<>();
            Class<?> aspectClass = Class.forName(this.config.getAspectClass());
            Map<String, Method> aspectMethods = new HashMap<>();
            for (Method method : aspectClass.getMethods()) {
                aspectMethods.put(method.getName(), method);
            }
            Pattern pointcutMethodPattern = Pattern.compile(pointcut.replaceAll("\\.", "\\\\."));
            for (Method method : this.targetClass.getMethods()) {
                String methodString = method.toString();
                Matcher matcher = pointcutMethodPattern.matcher(methodString);
                if (matcher.find()) {
                    Map<String, AdviceAspect> advices = new HashMap<>();
                    Object aspectObject = aspectClass.newInstance();
                    if (StrUtil.isNotBlank(this.config.getAspectBefore())
                        && aspectMethods.containsKey(this.config.getAspectBefore())) {
                        advices.put("before",
                            new AdviceAspect(aspectObject, aspectMethods.get(this.config.getAspectBefore())));
                    }
                    if (StrUtil.isNotBlank(this.config.getAspectAfter())
                        && aspectMethods.containsKey(this.config.getAspectAfter())) {
                        advices.put("after",
                            new AdviceAspect(aspectObject, aspectMethods.get(this.config.getAspectAfter())));
                    }
                    this.methodCache.put(method, advices);
                    Class<?>[] interfaces = this.targetClass.getInterfaces();
                    for (Class<?> clazz : interfaces) {
                        try {
                            Method m = clazz.getMethod(method.getName(), method.getParameterTypes());
                            this.methodCache.put(m, advices);
                        } catch (Exception ignore) {
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
