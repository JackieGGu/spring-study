package cn.jackiegu.spring.kkb.v2.registry;

import cn.jackiegu.spring.kkb.v1.ioc.BeanDefinition;

import java.util.List;

/**
 * 定义BeanDefinition容器的方法
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String name);

    List<BeanDefinition> getBeanDefinitions();

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
