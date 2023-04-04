package cn.jackiegu.spring.auto.configuration.boot.controller;

import cn.jackiegu.dependency.model.User;
import cn.jackiegu.spring.auto.configuration.boot.support.SpringContextSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Boot 自动装配启动测试接口
 *
 * @author JackieGu
 * @date 2021/7/15
 */
@RestController
@RequestMapping
public class SpringAutoConfigurationBootController {

    @RequestMapping("get/user")
    public User getUser() {
        try {
            return SpringContextSupport.getBean("user", User.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
