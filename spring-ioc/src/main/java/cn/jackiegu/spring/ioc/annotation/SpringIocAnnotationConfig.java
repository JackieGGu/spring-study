package cn.jackiegu.spring.ioc.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:applicationContext-ioc-annotation.properties", encoding = "UTF-8")
public class SpringIocAnnotationConfig {

    @Value("${work}")
    private String work;

    public String getWork() {
        return work;
    }
}
