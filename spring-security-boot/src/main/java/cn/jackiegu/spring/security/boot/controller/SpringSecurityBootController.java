package cn.jackiegu.spring.security.boot.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

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
    public ModelAndView login(boolean error, HttpSession session) {
        ModelAndView view = new ModelAndView();
        view.setViewName("custom-login");
        if (error) {
            AuthenticationException ex = (AuthenticationException) session
                .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            view.addObject("errorMessage", (ex != null) ? ex.getMessage() : "ERROR");
        }
        return view;
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
