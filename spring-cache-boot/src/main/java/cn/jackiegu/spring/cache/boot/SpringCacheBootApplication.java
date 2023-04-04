package cn.jackiegu.spring.cache.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Spring Boot Cache
 *
 * @author JackieGu
 * @date 2021/9/7
 */
@EnableCaching
@SpringBootApplication
public class SpringCacheBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCacheBootApplication.class, args);
    }
}
