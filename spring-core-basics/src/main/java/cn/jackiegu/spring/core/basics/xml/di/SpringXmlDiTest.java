package cn.jackiegu.spring.core.basics.xml.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring XML DI测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringXmlDiTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-di.xml");
        SpringXmlDiBean bean1 = (SpringXmlDiBean) applicationContext.getBean("bean1");
        System.out.println("bean1: " + bean1);

        SpringXmlDiBean bean2 = (SpringXmlDiBean) applicationContext.getBean("bean2");
        System.out.println("bean2: " + bean2);

        SpringXmlDiBean bean3 = (SpringXmlDiBean) applicationContext.getBean("bean3");
        System.out.println("bean3: " + bean3);

        SpringXmlDiBean bean4 = (SpringXmlDiBean) applicationContext.getBean("bean4");
        System.out.println("bean4: " + bean4);
    }
}
