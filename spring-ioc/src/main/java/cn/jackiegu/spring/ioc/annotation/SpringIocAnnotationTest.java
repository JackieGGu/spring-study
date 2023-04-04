package cn.jackiegu.spring.ioc.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring Annotation测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringIocAnnotationTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringIocAnnotationConfiguration.class);
        SpringIocAnnotationBean bean1 = (SpringIocAnnotationBean) applicationContext.getBean("bean1");
        System.out.println("bean1: " + bean1);
        SpringIocAnnotationComponent bean2 = applicationContext.getBean(SpringIocAnnotationComponent.class);
        System.out.println("bean2: " + bean2);
        SpringIocAnnotationConfig bean3 = applicationContext.getBean("springIocAnnotationConfig", SpringIocAnnotationConfig.class);
        System.out.println("bean3: " + bean3.getWork());

    }
}
