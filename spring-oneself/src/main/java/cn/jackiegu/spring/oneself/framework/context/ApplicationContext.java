package cn.jackiegu.spring.oneself.framework.context;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;
import cn.jackiegu.spring.oneself.framework.annotation.Autowired;
import cn.jackiegu.spring.oneself.framework.annotation.Controller;
import cn.jackiegu.spring.oneself.framework.annotation.Service;
import cn.jackiegu.spring.oneself.framework.aop.JdkDynamicAopProxy;
import cn.jackiegu.spring.oneself.framework.aop.config.AopConfig;
import cn.jackiegu.spring.oneself.framework.aop.support.AdviceSupport;
import cn.jackiegu.spring.oneself.framework.beans.BeanWrapper;
import cn.jackiegu.spring.oneself.framework.beans.config.BeanDefinition;
import cn.jackiegu.spring.oneself.framework.beans.support.BeanDefinitionReader;
import cn.jackiegu.spring.oneself.framework.exception.BeanCreationException;
import cn.jackiegu.spring.oneself.util.AopTargetUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring 应用上下文类
 *
 * @author JackieGu
 * @date 2021/2/2
 */
public class ApplicationContext {

    private static final Log LOGGER = LogFactory.get();

    /**
     * SpringBean定义阅读器
     */
    private final BeanDefinitionReader beanDefinitionReader;

    /**
     * Bean定义容器
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * IOC一级缓存
     */
    private final Map<String, BeanWrapper> singletonObjects = new ConcurrentHashMap<>();

    /**
     * AOP配置
     */
    private AopConfig aopConfig;

    /**
     * 应用上下文构造方法
     *
     * @param configLocation 配置文件位置
     */
    public ApplicationContext(String configLocation) {
        LOGGER.info("Instantiate ApplicationContext");
        this.beanDefinitionReader = new BeanDefinitionReader(configLocation);
        List<BeanDefinition> beanDefinitions = this.beanDefinitionReader.loadBeanDefinitions();
        try {
            this.registerBeanDefinition(beanDefinitions);
            this.autowired();
        } catch (BeanCreationException e) {
            LOGGER.error(e);
        }
    }

    /**
     * 获取所有定义Bean名称
     */
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[0]);
    }

    /**
     * 获取所有定义Bean数量
     */
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    /**
     * 获取Bean对象
     *
     * @param name bean名称
     */
    public Object getBean(String name) {
        String beanName = this.beanDefinitionReader.canonicalName(name);
        synchronized (this.singletonObjects) {
            if (this.singletonObjects.containsKey(beanName)) {
                return this.singletonObjects.get(beanName).getWrapperInstance();
            }
            BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
            if (beanDefinition == null) {
                throw new BeanCreationException("The " + beanName + " is undefined");
            }
            Object instance = this.createBeanInstance(beanDefinition);
            BeanWrapper beanWrapper;
            try {
                beanWrapper = new BeanWrapper(instance);
            } catch (Exception e) {
                throw new BeanCreationException("Create bean '" + beanName + "' exception", e);
            }
            this.singletonObjects.put(beanName, beanWrapper);
            this.populateBean(beanWrapper);
            return instance;
        }
    }

    /**
     * 获取应用配置
     */
    public Props getConfig() {
        return this.beanDefinitionReader.getConfig();
    }

    /**
     * 注册Bean定义实例, 并放入IOC容器
     */
    private void registerBeanDefinition(List<BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (this.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new BeanCreationException("The " + beanDefinition.getFactoryBeanName() + " is exist");
            }
            this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
    }

    /**
     * 自动注入
     */
    private void autowired() {
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : this.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            this.getBean(beanName);
        }
    }

    /**
     * 实例Bean对象
     *
     * @param beanDefinition bean 定义
     */
    private Object createBeanInstance(BeanDefinition beanDefinition) {
        String beanClassName = beanDefinition.getBeanClassName();
        Object instance = null;
        try {
            Class<?> clazz = Class.forName(beanClassName);
            instance = clazz.newInstance();
            AdviceSupport adviceSupport = this.getAdviceSupport();
            adviceSupport.setTarget(instance);
            adviceSupport.setTargetClass(clazz);
            if (adviceSupport.pointcutMatch()) {
                instance = new JdkDynamicAopProxy(adviceSupport).getProxy();
            }
            LOGGER.info("Instantiate bean: {}", beanClassName);
        } catch (Exception e) {
            LOGGER.error(e, "实例Bean对象异常");
        }
        return instance;
    }

    /**
     * 对bean执行依赖注入
     *
     * @param beanWrapper bean包装类
     */
    private void populateBean(BeanWrapper beanWrapper) {
        Object instance = beanWrapper.getWrapperInstance();
        if (instance instanceof Proxy) {
            try {
                AdviceSupport adviceSupport = AopTargetUtil
                    .getTargetFieldValue(instance, JdkDynamicAopProxy.class, "adviceSupport", AdviceSupport.class);
                instance = adviceSupport.getTarget();
            } catch (Exception e) {
                LOGGER.error(e, "获取代理对象实际对象失败");
            }
        }
        Class<?> clazz = beanWrapper.getWrapperClass();
        if (!clazz.isAnnotationPresent(Controller.class) && !clazz.isAnnotationPresent(Service.class)) {
            return;
        }
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)) {
                    continue;
                }
                String autowiredBeanName = field.getType().getName();
                field.setAccessible(true);
                field.set(instance, this.getBean(autowiredBeanName));
            }
        } catch (Exception e) {
            LOGGER.error(e, "自动依赖注入异常");
        }
    }

    /**
     * 创建通知支持类
     */
    private AdviceSupport getAdviceSupport() {
        if (this.aopConfig == null) {
            this.aopConfig = new AopConfig();
            this.aopConfig.setPointcut(this.getConfig().getStr("pointcut"));
            this.aopConfig.setAspectClass(this.getConfig().getStr("aspect.class"));
            this.aopConfig.setAspectBefore(this.getConfig().getStr("aspect.before"));
            this.aopConfig.setAspectAfter(this.getConfig().getStr("aspect.after"));
        }
        return new AdviceSupport(aopConfig);
    }
}
