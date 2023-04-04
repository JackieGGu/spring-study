package cn.jackiegu.spring.aop.mixture;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring基于Annotation和XML混合的AOP测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringAopMixtureTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-aop-mixture.xml");
        SpringAopMixtureService springAopMixtureService = applicationContext.getBean(SpringAopMixtureService.class);
        String name1 = springAopMixtureService.findName(1);
        System.out.println("name1: " + name1);

        System.out.println();
        String name2 = springAopMixtureService.findName(2);
        System.out.println("name2: " + name2);

        System.out.println();
        String name99 = springAopMixtureService.findName(99);
        System.out.println("name99: " + name99);
    }
}
