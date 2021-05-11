package cn.jackiegu.spring.core.advanced.annotation.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring基于Annotation的AOP测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringAnnotationAopTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringAnnotationAopConfiguration.class);
        SpringAnnotationAopService springAnnotationAopService = applicationContext.getBean(SpringAnnotationAopService.class);
        String name1 = springAnnotationAopService.findName(1);
        System.out.println("name1: " + name1);

        System.out.println();
        String name2 = springAnnotationAopService.findName(99);
        System.out.println("name2: " + name2);
    }
}
