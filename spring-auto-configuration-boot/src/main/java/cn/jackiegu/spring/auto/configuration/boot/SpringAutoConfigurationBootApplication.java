package cn.jackiegu.spring.auto.configuration.boot;

import cn.jackiegu.dependency.EnableDependency;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 自动装配启动测试类
 *
 * @author JackieGu
 * @date 2021/7/15
 */
@SpringBootApplication
@EnableDependency
public class SpringAutoConfigurationBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAutoConfigurationBootApplication.class, args);
    }
}
