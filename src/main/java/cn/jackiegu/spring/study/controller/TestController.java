package cn.jackiegu.spring.study.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.jackiegu.spring.study.framework.annotation.Autowired;
import cn.jackiegu.spring.study.framework.annotation.Controller;
import cn.jackiegu.spring.study.framework.annotation.RequestMapping;
import cn.jackiegu.spring.study.framework.annotation.RequestParam;
import cn.jackiegu.spring.study.service.TestService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("test")
public class TestController {

    private static final Log LOGGER = LogFactory.get();

    @Autowired
    private TestService testService;

    @RequestMapping("hello")
    public String hello(HttpServletResponse response) {
        LOGGER.info(response.toString());
        return testService.hello();
    }

    @RequestMapping("sayHi")
    public String sayHi(@RequestParam("name") String name,
                        String none,
                        @RequestParam("number") Integer[] number) {
        LOGGER.info("none value: {}", none);
        return testService.sayHi(name, number);
    }
}
