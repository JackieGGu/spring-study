package cn.jackiegu.spring.oneself.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.jackiegu.spring.oneself.domain.User;
import cn.jackiegu.spring.oneself.framework.annotation.Autowired;
import cn.jackiegu.spring.oneself.framework.annotation.Service;
import cn.jackiegu.spring.oneself.service.TestService;
import cn.jackiegu.spring.oneself.service.UserService;

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
        return "Hello Spring MVC " + DateTime.now();
    }

    @Override
    public User sayHi(String name, Integer sex, Integer age) {
        User user = new User();
        user.setName(name);
        user.setSex(sex);
        user.setAge(age);
        return this.userService.save(user);
    }

    @Override
    public String getTime() {
        return DateUtil.now();
    }
}
