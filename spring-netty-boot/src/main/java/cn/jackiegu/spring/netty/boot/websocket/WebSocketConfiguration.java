package cn.jackiegu.spring.netty.boot.websocket;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * websocket配置类
 *
 * @author JackieGu
 * @date 2021/9/8
 */
@Configuration
@EnableConfigurationProperties({WebSocketProperties.class})
public class WebSocketConfiguration {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public WebSocketServer webSocketServer(WebSocketProperties webSocketProperties) {
        return new WebSocketServer(webSocketProperties);
    }
}
