package cn.jackiegu.spring.study.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.jackiegu.spring.study.domain.User;
import cn.jackiegu.spring.study.framework.annotation.Autowired;
import cn.jackiegu.spring.study.framework.annotation.Controller;
import cn.jackiegu.spring.study.framework.annotation.RequestMapping;
import cn.jackiegu.spring.study.framework.annotation.RequestParam;
import cn.jackiegu.spring.study.framework.webmvc.servlet.ModelAndView;
import cn.jackiegu.spring.study.service.TestService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
    public User sayHi(@RequestParam("name") String name,
                      Integer sex,
                      @RequestParam("age") Integer age) {
        return testService.sayHi(name, sex, age);
    }

    @RequestMapping("spring")
    public ModelAndView spring(@RequestParam("name") String name) {
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        model.put("time", testService.getTime());
        return new ModelAndView("spring", model);
    }
}
