package cn.jackiegu.spring.core.basics.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring Annotation测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringAnnotationTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringAnnotationConfiguration.class);
        SpringAnnotationBean bean1 = (SpringAnnotationBean) applicationContext.getBean("bean1");
        System.out.println("bean1: " + bean1);
        SpringAnnotationComponent bean2 = applicationContext.getBean(SpringAnnotationComponent.class);
        System.out.println("bean2: " + bean2);
        SpringAnnotationConfig bean3 = applicationContext.getBean("springAnnotationConfig", SpringAnnotationConfig.class);
        System.out.println("bean3: " + bean3.getWork());

    }
}
