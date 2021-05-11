package cn.jackiegu.spring.ioc.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring XML DI测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringDiXmlTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-di-xml.xml");
        SpringDiXmlBean bean1 = (SpringDiXmlBean) applicationContext.getBean("bean1");
        System.out.println("bean1: " + bean1);

        SpringDiXmlBean bean2 = (SpringDiXmlBean) applicationContext.getBean("bean2");
        System.out.println("bean2: " + bean2);

        SpringDiXmlBean bean3 = (SpringDiXmlBean) applicationContext.getBean("bean3");
        System.out.println("bean3: " + bean3);

        SpringDiXmlBean bean4 = (SpringDiXmlBean) applicationContext.getBean("bean4");
        System.out.println("bean4: " + bean4);
    }
}
