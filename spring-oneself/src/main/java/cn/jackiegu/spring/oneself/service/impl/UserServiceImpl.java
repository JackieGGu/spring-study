package cn.jackiegu.spring.oneself.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.jackiegu.spring.oneself.domain.User;
import cn.jackiegu.spring.oneself.framework.annotation.Autowired;
import cn.jackiegu.spring.oneself.framework.annotation.Service;
import cn.jackiegu.spring.oneself.service.TestService;
import cn.jackiegu.spring.oneself.service.UserService;
import cn.jackiegu.spring.oneself.util.SnowflakeUtil;

/**
 * 用户Service实现
 *
 * @author JackieGu
 * @date 2021/2/3
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Log LOGGER = LogFactory.get();

    @Autowired
    private TestService testService;

    @Override
    public User save(User user) {
        user.setId(SnowflakeUtil.nextId());
        return user;
    }
}
