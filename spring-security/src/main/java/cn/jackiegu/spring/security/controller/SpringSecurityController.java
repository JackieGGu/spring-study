package cn.jackiegu.spring.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring Security 测试接口
 *
 * @author JackieGu
 * @date 2021/6/4
 */
@RestController
@RequestMapping("security")
public class SpringSecurityController {

    @RequestMapping("hello")
    public Map<String, Object> hello() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("msg", "success");
        return map;
    }
}
