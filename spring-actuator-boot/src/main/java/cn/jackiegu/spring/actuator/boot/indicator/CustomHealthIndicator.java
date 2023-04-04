package cn.jackiegu.spring.actuator.boot.indicator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * 自定义健康指标
 *
 * @author JackieGu
 * @date 2021/9/13
 */
@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        builder.up()
            .withDetail("status", "ok");
    }
}
