package cn.jackiegu.spring.kkb.v2.reader;

import cn.hutool.core.util.StrUtil;
import cn.jackiegu.spring.kkb.v1.ioc.BeanDefinition;
import cn.jackiegu.spring.kkb.v1.ioc.PropertyValue;
import cn.jackiegu.spring.kkb.v1.ioc.RuntimeBeanReference;
import cn.jackiegu.spring.kkb.v1.ioc.TypedStringValue;
import cn.jackiegu.spring.kkb.v2.registry.BeanDefinitionRegistry;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Default BeanDefinition文档阅读器
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public DefaultBeanDefinitionDocumentReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void registerBeanDefinition(Element rootElement) {
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
            beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
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
}
