package cn.jackiegu.spring.ioc.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan
@Import({SpringIocAnnotationConfig.class})
public class SpringIocAnnotationConfiguration {

    @Bean("bean1")
    @Scope("prototype")
    public SpringIocAnnotationBean springAnnotationBean() {
        SpringIocAnnotationBean springIocAnnotationBean = new SpringIocAnnotationBean();
        springIocAnnotationBean.setName("spring ioc annotation bean");
        return springIocAnnotationBean;
    }
}
