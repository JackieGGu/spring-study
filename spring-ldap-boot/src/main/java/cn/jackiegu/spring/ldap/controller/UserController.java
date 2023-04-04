package cn.jackiegu.spring.ldap.controller;

import cn.jackiegu.spring.ldap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller
 *
 * @author JackieGu
 * @date 2021/11/1
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public Boolean login(String username, String password) {
        return userService.login(username, password);
    }
}
