package cn.jackiegu.spring.kkb.v2.factory;

import cn.jackiegu.spring.kkb.v2.reader.BeanDefinitionReader;
import cn.jackiegu.spring.kkb.v2.reader.XmlBeanDefinitionReader;
import cn.jackiegu.spring.kkb.v2.resource.Resource;

/**
 * @author JackieGu
 * @date 2021/5/19
 */
public class XmlBeanFactory extends DefaultListableBeanFactory {

    public XmlBeanFactory(String location) {
        Resource resource = Resource.getInstance(location);
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(this);
        beanDefinitionReader.loadBeanDefinitions(resource);
    }
}
