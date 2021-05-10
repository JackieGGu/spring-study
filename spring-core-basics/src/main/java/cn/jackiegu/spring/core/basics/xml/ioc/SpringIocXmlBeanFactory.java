package cn.jackiegu.spring.core.basics.xml.ioc;

/**
 * IOC XML bean对象的创建工厂
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringIocXmlBeanFactory {

    public SpringIocXmlBean generate() {
        return new SpringIocXmlBean();
    }
}
