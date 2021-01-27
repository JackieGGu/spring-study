package cn.jackiegu.spring.study.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.jackiegu.spring.study.annotation.Autowired;
import cn.jackiegu.spring.study.annotation.Controller;
import cn.jackiegu.spring.study.annotation.RequestMapping;
import cn.jackiegu.spring.study.service.TestService;

@Controller
@RequestMapping("test")
public class TestController {

    private static final Log LOGGER = LogFactory.get();

    @Autowired
    private TestService testService;

    @RequestMapping("hello")
    public String hello() {
        LOGGER.info("Hello Spring MVC");
        return "Hello Spring MVC";
    }

    @RequestMapping("sayHi")
    public String sayHi(String name) {
        LOGGER.info("Hi " + name);
        return "Hi " + name;
    }
}
