package cn.jackiegu.spring.security.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Security 测试接口
 *
 * @author JackieGu
 * @date 2021/7/2
 */
@RestController
@RequestMapping("security/boot")
public class SpringSecurityBootController {

    @RequestMapping("hello")
    public String hello() {
        return "Hello Spring Security";
    }

    @RequestMapping("action")
    public String action() {
        return "Do Something";
    }

    // @RequestMapping("other")
    // @PreAuthorize("hasRole('TEST')")
    // public String other() {
    //     return "Other Operation";
    // }
}
