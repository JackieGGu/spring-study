package cn.jackiegu.spring.cache.boot.controller;

import cn.jackiegu.spring.cache.boot.model.User;
import cn.jackiegu.spring.cache.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 *
 * @author JackieGu
 * @date 2021/9/7
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("add")
    public String add(String name, Integer age, String sex) {
        userService.addUser(name, age, sex);
        return "success";
    }

    @GetMapping("get")
    public User get(String name) {
        return userService.getUser(name);
    }
}
