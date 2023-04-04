package cn.jackiegu.spring.kkb.v2.reader;

import cn.jackiegu.spring.kkb.v2.registry.BeanDefinitionRegistry;
import cn.jackiegu.spring.kkb.v2.resource.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Assert;

import java.io.InputStream;

/**
 * XML BeanDefinition阅读器
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public class XmlBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void loadBeanDefinitions(Resource resource) {
        InputStream is = resource.getInputStream();
        BeanDefinitionDocumentReader documentReader = new DefaultBeanDefinitionDocumentReader(beanDefinitionRegistry);
        Document document = getDocument(is);
        Assert.assertNotNull(document);
        documentReader.registerBeanDefinition(document.getRootElement());
    }

    private Document getDocument(InputStream is) {
        try {
            SAXReader saxReader = new SAXReader();
            return saxReader.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
