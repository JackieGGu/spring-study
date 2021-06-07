package cn.jackiegu.spring.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Security 测试接口
 *
 * @author JackieGu
 * @date 2021/6/4
 */
@RestController
@RequestMapping("security")
public class SpringSecurityController {

    @RequestMapping("dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
