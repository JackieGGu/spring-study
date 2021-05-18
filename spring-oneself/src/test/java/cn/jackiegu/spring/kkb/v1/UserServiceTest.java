package cn.jackiegu.spring.kkb.v1;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.jackiegu.spring.kkb.business.entity.UserEntity;
import cn.jackiegu.spring.kkb.v1.ioc.BeanDefinition;
import cn.jackiegu.spring.kkb.v1.ioc.PropertyValue;
import cn.jackiegu.spring.kkb.v1.ioc.RuntimeBeanReference;
import cn.jackiegu.spring.kkb.v1.ioc.TypedStringValue;
import cn.jackiegu.spring.kkb.business.service.UserService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * UserService测试
 *
 * @author JackieGu
 * @date 2021/5/12
 */
public class UserServiceTest {

    private static final String configLocation = "beans.xml";

    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    private final Map<String, Object> singletonObjects = new HashMap<>();

    @Before
    public void before() {
        Document document = getDocument(configLocation);
        Assert.assertNotNull(document);
        loadBeanDefinitions(document.getRootElement());
    }

    @Test
    public void run() {
        UserService userService = (UserService) getBean("userService");
        Assert.assertNotNull(userService);
        UserEntity entity = userService.findById(1);
        System.out.println(entity);
    }

    private Document getDocument(String configLocation) {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(configLocation);
            SAXReader saxReader = new SAXReader();
            return saxReader.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void loadBeanDefinitions(Element rootElement) {
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            if ("bean".equals(element.getName())) {
                parseDefaultElement(element);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void parseDefaultElement(Element element) {
        try {
            String beanName = element.attributeValue("id");
            String clazzName = element.attributeValue("class");
            Class<?> clazzType = Class.forName(clazzName);
            String initMethod = element.attributeValue("init-method");
            String scope = element.attributeValue("scope");

            beanName = StrUtil.isBlank(beanName) ? clazzType.getSimpleName() : beanName;
            scope = StrUtil.isBlank(scope) ? "singleton" : scope;

            BeanDefinition beanDefinition = new BeanDefinition(beanName, clazzName);
            beanDefinition.setInitMethod(initMethod);
            beanDefinition.setScope(scope);

            List<Element> elements = element.elements();
            parsePropertyElements(beanDefinition, elements);
            this.beanDefinitions.put(beanName, beanDefinition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parsePropertyElements(BeanDefinition beanDefinition, List<Element> elements) {
        try {
            for (Element element : elements) {
                String name = element.attributeValue("name");
                String value = element.attributeValue("value");
                String ref = element.attributeValue("ref");
                if (StrUtil.isNotBlank(value)) {
                    TypedStringValue typedStringValue = new TypedStringValue(value);
                    Class<?> clazzType = beanDefinition.getClazzType();
                    Field field = clazzType.getDeclaredField(name);
                    typedStringValue.setTargetType(field.getType());
                    beanDefinition.addPropertyValue(new PropertyValue(name, typedStringValue));
                } else if (StrUtil.isNotBlank(ref)) {
                    RuntimeBeanReference reference = new RuntimeBeanReference(ref);
                    PropertyValue propertyValue = new PropertyValue(name, reference);
                    beanDefinition.addPropertyValue(propertyValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getBean(String beanName) {
        Object bean = this.singletonObjects.get(beanName);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = this.beanDefinitions.get(beanName);
        if (beanDefinition == null) {
            throw new NoSuchElementException("No value present");
        }
        if (beanDefinition.isSingleton()) {
            bean = createBean(beanDefinition);
            this.singletonObjects.put(beanName, bean);
            return bean;
        } else if (beanDefinition.isPrototype()) {
            return createBean(beanDefinition);
        } else {
            return null;
        }
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Object bean = createInstance(beanDefinition);
        populateBean(beanDefinition, bean);
        initializingBean(beanDefinition, bean);
        return bean;
    }

    private Object createInstance(BeanDefinition beanDefinition) {
        try {
            // 通过bean工厂去创建

            // 通过静态工厂去创建

            // 通过反射去创建
            Class<?> clazzType = beanDefinition.getClazzType();
            Constructor<?> constructor = clazzType.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void populateBean(BeanDefinition beanDefinition, Object bean) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            Object valueToUse = resolveValue(value);
            setProperty(bean, name, valueToUse);
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

    private void setProperty(Object bean, String name, Object valueToUse) {
        try {
            Class<?> clazz = bean.getClass();
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(bean, valueToUse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializingBean(BeanDefinition beanDefinition, Object bean) {
        // 针对Bean对象执行Aware接口

        invokeInitMethod(beanDefinition, bean);
    }

    private void invokeInitMethod(BeanDefinition beanDefinition, Object bean) {
        try {
            String initMethodName = beanDefinition.getInitMethod();
            if (StrUtil.isNotBlank(initMethodName)) {
                Class<?> clazz = beanDefinition.getClazzType();
                Method initMethod = clazz.getDeclaredMethod(initMethodName);
                initMethod.invoke(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
