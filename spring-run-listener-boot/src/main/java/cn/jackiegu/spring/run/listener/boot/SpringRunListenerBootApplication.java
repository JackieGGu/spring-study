package cn.jackiegu.spring.run.listener.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 启动生命周期监听器测试类
 *
 * @author JackieGu
 * @date 2022/1/10
 */
@SpringBootApplication
public class SpringRunListenerBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRunListenerBootApplication.class, args);
    }
}
