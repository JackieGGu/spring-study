package cn.jackiegu.spring.study.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.jackiegu.spring.study.domain.User;
import cn.jackiegu.spring.study.framework.annotation.Autowired;
import cn.jackiegu.spring.study.framework.annotation.Service;
import cn.jackiegu.spring.study.service.TestService;
import cn.jackiegu.spring.study.service.UserService;

/**
 * 测试Service实现
 *
 * @author JackieGu
 * @date 2021/2/3
 */
@Service
public class TestServiceImpl implements TestService {

    private static final Log LOGGER = LogFactory.get();

    @Autowired
    private UserService userService;

    @Override
    public String hello() {
        return "Hello Spring MVC " + DateTime.now().toString();
    }

    @Override
    public String sayHi(String name, Integer sex, Integer age) {
        User user = new User();
        user.setName(name);
        user.setSex(sex);
        user.setAge(age);
        userService.save(user);
        return "Success";
    }

    @Override
    public String getTime() {
        return DateUtil.now();
    }
}
