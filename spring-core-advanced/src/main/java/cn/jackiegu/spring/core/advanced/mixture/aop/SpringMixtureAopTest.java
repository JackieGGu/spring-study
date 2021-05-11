package cn.jackiegu.spring.core.advanced.mixture.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring基于Annotation和XML混合的AOP测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringMixtureAopTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-mixture-aop.xml");
        SpringMixtureAopService springMixtureAopService = applicationContext.getBean(SpringMixtureAopService.class);
        String name1 = springMixtureAopService.findName(1);
        System.out.println("name1: " + name1);

        System.out.println();
        String name2 = springMixtureAopService.findName(2);
        System.out.println("name2: " + name2);

        System.out.println();
        String name99 = springMixtureAopService.findName(99);
        System.out.println("name99: " + name99);
    }
}
