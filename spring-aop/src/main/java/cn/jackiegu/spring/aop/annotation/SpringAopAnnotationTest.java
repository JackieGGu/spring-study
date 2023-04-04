package cn.jackiegu.spring.aop.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring基于Annotation的AOP测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringAopAnnotationTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringAopAnnotationConfiguration.class);
        SpringAopAnnotationService springAopAnnotationService = applicationContext.getBean(SpringAopAnnotationService.class);
        String name1 = springAopAnnotationService.findName(1);
        System.out.println("name1: " + name1);

        System.out.println();
        String name2 = springAopAnnotationService.findName(99);
        System.out.println("name2: " + name2);
    }
}
