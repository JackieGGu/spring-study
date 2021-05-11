package cn.jackiegu.spring.ioc.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring XML IOC测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringXmlIocTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-ioc.xml");
        SpringXmlIocBean bean1 = (SpringXmlIocBean) applicationContext.getBean("bean1");
        System.out.println("bean1: " + bean1.getNumber() + ", " + bean1.hashCode());

        SpringXmlIocBean bean2 = (SpringXmlIocBean) applicationContext.getBean("bean2");
        System.out.println("bean2: " + bean2.getNumber() + ", " + bean2.hashCode());

        SpringXmlIocBean bean3 = (SpringXmlIocBean) applicationContext.getBean("bean3");
        System.out.println("bean3: " + bean3.getNumber() + ", " + bean3.hashCode());
    }
}
