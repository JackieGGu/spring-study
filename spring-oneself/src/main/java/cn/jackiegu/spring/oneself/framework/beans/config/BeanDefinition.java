package cn.jackiegu.spring.oneself.framework.beans.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * Spring Bean 定义类
 *
 * @author JackieGu
 * @date 2021/2/2
 */
public class BeanDefinition {

    private static final Log LOGGER = LogFactory.get();

    private String factoryBeanName;

    private String beanClassName;

    public BeanDefinition(String factoryBeanName, String beanClassName) {
        this.factoryBeanName = factoryBeanName;
        this.beanClassName = beanClassName;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }
}
