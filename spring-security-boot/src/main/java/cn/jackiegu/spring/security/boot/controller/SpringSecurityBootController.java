package cn.jackiegu.spring.security.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Spring Security 测试接口
 *
 * @author JackieGu
 * @date 2021/7/2
 */
@RestController
@RequestMapping("security/boot")
public class SpringSecurityBootController {

    /**
     * 自定义登录页
     */
    @RequestMapping("login")
    public ModelAndView login() {
        return new ModelAndView("custom-login");
    }

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
