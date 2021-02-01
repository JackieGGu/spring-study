package cn.jackiegu.spring.study.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.jackiegu.spring.study.framework.annotation.Service;
import cn.jackiegu.spring.study.service.TestService;

import java.util.Arrays;

@Service
public class TestServiceImpl implements TestService {

    private static final Log LOGGER = LogFactory.get();

    @Override
    public String hello() {
        return "Hello Spring MVC";
    }

    @Override
    public String sayHi(String name, Integer[] number) {
        return "Hi " + name + Arrays.toString(number);
    }
}
