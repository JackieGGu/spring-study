package cn.jackiegu.spring.netty.boot.websocket;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * websocket属性配置类
 *
 * @author JackieGu
 * @date 2021/9/8
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "netty.websocket")
public class WebSocketProperties {

    private String host = "0.0.0.0";

    private Integer port = 8888;

    private String path = "/ws";
}
