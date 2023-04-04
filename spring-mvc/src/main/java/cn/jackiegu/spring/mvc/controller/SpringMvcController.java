package cn.jackiegu.spring.mvc.controller;

import cn.jackiegu.spring.mvc.service.SpringMvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Spring MVC 控制器
 *
 * @author JackieGu
 * @date 2021/6/2
 */
@Controller
@RequestMapping("mvc")
public class SpringMvcController {

    @Autowired
    private SpringMvcService springMvcService;

    @ResponseBody
    @RequestMapping("hello")
    public String hello() {
        return "hello " + springMvcService.generateNumber();
    }
}
