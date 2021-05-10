package cn.jackiegu.spring.core.basics.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:applicationContext-annotation.properties", encoding = "UTF-8")
public class SpringAnnotationConfig {

    @Value("${work}")
    private String work;

    public String getWork() {
        return work;
    }
}
