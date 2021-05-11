package cn.jackiegu.spring.ioc.mixture;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring XML和Annotation混合使用测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringIocMixtureTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-ioc-mixture.xml");
        SpringIocMixtureService springIocMixtureService = applicationContext.getBean(SpringIocMixtureService.class);
        springIocMixtureService.doSomething();
    }
}
