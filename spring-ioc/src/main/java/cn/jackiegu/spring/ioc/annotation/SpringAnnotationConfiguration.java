package cn.jackiegu.spring.ioc.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan
// @Import({SpringAnnotationConfig.class})
public class SpringAnnotationConfiguration {

    @Bean("bean1")
    @Scope("prototype")
    public SpringAnnotationBean springAnnotationBean() {
        SpringAnnotationBean springAnnotationBean = new SpringAnnotationBean();
        springAnnotationBean.setName("spring annotation bean");
        return springAnnotationBean;
    }
}
