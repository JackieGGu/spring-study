package cn.jackiegu.spring.kkb.v2.factory;

import cn.jackiegu.spring.kkb.v1.ioc.BeanDefinition;
import cn.jackiegu.spring.kkb.v2.registry.DefaultSingletonBeanRegistry;

import java.util.NoSuchElementException;

/**
 * 抽象Bean工厂类
 * 制定getBean的处理流程
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        if (beanDefinition == null) {
            throw new NoSuchElementException("No value present");
        }
        if (beanDefinition.isSingleton()) {
            bean = createBean(beanDefinition);
            addSingleton(name, bean);
            return bean;
        } else if (beanDefinition.isPrototype()) {
            return createBean(beanDefinition);
        } else {
            return null;
        }
    }

    /**
     * 该方法需要延迟到DefaultListableBeanFactory处理
     * 采用模板方法模式
     */
    protected abstract BeanDefinition getBeanDefinition(String name);

    /**
     * 该方法需要延迟到AbstractAutowireCapableBeanFactory处理
     * 采用模板方法模式
     */
    protected abstract Object createBean(BeanDefinition beanDefinition);
}
