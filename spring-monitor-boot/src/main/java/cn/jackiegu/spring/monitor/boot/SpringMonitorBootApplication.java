package cn.jackiegu.spring.monitor.boot;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author JackieGu
 * @date 2021/8/7
 */
@EnableAdminServer
@SpringBootApplication
public class SpringMonitorBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMonitorBootApplication.class, args);
    }
}
