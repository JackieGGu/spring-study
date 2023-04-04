package cn.jackiegu.spring.kkb.v2.factory;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.jackiegu.spring.kkb.util.ReflectUtils;
import cn.jackiegu.spring.kkb.v1.ioc.BeanDefinition;
import cn.jackiegu.spring.kkb.v1.ioc.PropertyValue;
import cn.jackiegu.spring.kkb.v1.ioc.RuntimeBeanReference;
import cn.jackiegu.spring.kkb.v1.ioc.TypedStringValue;

import java.util.List;

/**
 * 抽象依赖注入Bean工厂类
 * 负责bean的创建、依赖注入逻辑
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(BeanDefinition beanDefinition) {
        Object bean = createInstance(beanDefinition);
        populateBean(beanDefinition, bean);
        initializingBean(beanDefinition, bean);
        return bean;
    }

    private Object createInstance(BeanDefinition beanDefinition) {
        // 通过bean工厂去创建

        // 通过静态工厂去创建

        // 通过反射去创建
        return ReflectUtils.newInstance(beanDefinition.getClazzType());
    }

    private void populateBean(BeanDefinition beanDefinition, Object bean) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            Object valueToUse = resolveValue(value);
            ReflectUtils.setProperty(bean, name, valueToUse);
        }
    }

    private Object resolveValue(Object value) {
        if (value instanceof TypedStringValue) {
            TypedStringValue typedStringValue = (TypedStringValue) value;
            Object valueToUse = typedStringValue.getValue();
            Class<?> targetType = typedStringValue.getTargetType();
            valueToUse = Convert.convert(targetType, valueToUse);
            return valueToUse;
        } else if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference reference = (RuntimeBeanReference) value;
            // 这里容易引起循环依赖
            return getBean(reference.getRef());
        } else {
            return null;
        }
    }

    private void initializingBean(BeanDefinition beanDefinition, Object bean) {
        // 针对Bean对象执行Aware接口

        invokeInitMethod(beanDefinition, bean);
    }

    private void invokeInitMethod(BeanDefinition beanDefinition, Object bean) {
        String initMethodName = beanDefinition.getInitMethod();
        if (StrUtil.isNotBlank(initMethodName)) {
            ReflectUtils.invokeMethod(bean, initMethodName);
        }
    }
}
