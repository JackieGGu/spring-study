package cn.jackiegu.spring.kkb.v2.factory;

import cn.jackiegu.spring.kkb.v1.ioc.BeanDefinition;
import cn.jackiegu.spring.kkb.v2.registry.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认Listable Bean工厂
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return beanDefinitions.get(name);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions() {
        return null;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitions.put(name, beanDefinition);
    }
}
