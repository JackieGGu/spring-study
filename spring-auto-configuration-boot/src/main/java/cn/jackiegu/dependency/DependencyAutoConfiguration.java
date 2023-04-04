package cn.jackiegu.dependency;

import cn.jackiegu.dependency.model.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 模拟依赖包中的配置类
 *
 * @author JackieGu
 * @date 2021/7/15
 */
@Configuration
@ConditionalOnBean({DependencyMarker.class})
public class DependencyAutoConfiguration {

    @Bean
    public User user() {
        User user = new User();
        user.setName("球球");
        user.setAge(4);
        user.setSex("公");
        return user;
    }
}
