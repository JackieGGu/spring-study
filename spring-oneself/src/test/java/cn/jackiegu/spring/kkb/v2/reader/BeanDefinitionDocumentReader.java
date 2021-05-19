package cn.jackiegu.spring.kkb.v2.reader;

import org.dom4j.Element;

/**
 * BeanDefinition文档阅读器
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public interface BeanDefinitionDocumentReader {

    void registerBeanDefinition(Element element);
}
