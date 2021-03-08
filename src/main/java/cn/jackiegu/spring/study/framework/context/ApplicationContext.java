package cn.jackiegu.spring.study.framework.context;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;
import cn.jackiegu.spring.study.framework.annotation.Autowired;
import cn.jackiegu.spring.study.framework.annotation.Controller;
import cn.jackiegu.spring.study.framework.annotation.Service;
import cn.jackiegu.spring.study.framework.beans.BeanWrapper;
import cn.jackiegu.spring.study.framework.beans.config.BeanDefinition;
import cn.jackiegu.spring.study.framework.beans.support.BeanDefinitionReader;
import cn.jackiegu.spring.study.framework.exception.BeanCreationException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * IOC容器
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private final Map<Class<?>, Object> factoryBeanObjectCache = new HashMap<>();
    private final Map<String, BeanWrapper> factoryBeanInstanceCache = new HashMap<>();

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
     * @param beanName bean 名称
     */
    public Object getBean(String beanName) {
        if (this.factoryBeanInstanceCache.containsKey(beanName)) {
            return this.factoryBeanInstanceCache.get(beanName).getWrapperInstance();
        }
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeanCreationException("The " + beanName + " is undefined");
        }
        Object instance = this.instantiateBean(beanDefinition);
        BeanWrapper beanWrapper = new BeanWrapper(instance);
        this.factoryBeanInstanceCache.put(beanName, beanWrapper);
        this.populateBean(beanWrapper);
        return this.factoryBeanInstanceCache.get(beanName).getWrapperInstance();
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
    private Object instantiateBean(BeanDefinition beanDefinition) {
        String beanClassName = beanDefinition.getBeanClassName();
        Object instance = null;
        try {
            Class<?> clazz = Class.forName(beanClassName);
            if (this.factoryBeanObjectCache.containsKey(clazz)) {
                return this.factoryBeanObjectCache.get(clazz);
            }
            instance = clazz.newInstance();
            LOGGER.info("Instantiate bean: {}", beanClassName);
            this.factoryBeanObjectCache.put(clazz, instance);
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
}
