package cn.jackiegu.spring.ioc.mixture;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring XML和Annotation混合使用测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringMixtureTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-mixture.xml");
        SpringMixtureService springMixtureService = applicationContext.getBean(SpringMixtureService.class);
        springMixtureService.doSomething();
    }
}
