package cn.jackiegu.spring.ioc.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring XML IOC测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringIocXmlTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-ioc-xml.xml");
        SpringIocXmlBean bean1 = (SpringIocXmlBean) applicationContext.getBean("bean1");
        System.out.println("bean1: " + bean1.getNumber() + ", " + bean1.hashCode());

        SpringIocXmlBean bean2 = (SpringIocXmlBean) applicationContext.getBean("bean2");
        System.out.println("bean2: " + bean2.getNumber() + ", " + bean2.hashCode());

        SpringIocXmlBean bean3 = (SpringIocXmlBean) applicationContext.getBean("bean3");
        System.out.println("bean3: " + bean3.getNumber() + ", " + bean3.hashCode());
    }
}
