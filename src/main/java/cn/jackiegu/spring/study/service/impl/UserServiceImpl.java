package cn.jackiegu.spring.study.service.impl;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.jackiegu.spring.study.domain.User;
import cn.jackiegu.spring.study.framework.annotation.Autowired;
import cn.jackiegu.spring.study.framework.annotation.Service;
import cn.jackiegu.spring.study.service.TestService;
import cn.jackiegu.spring.study.service.UserService;
import cn.jackiegu.spring.study.util.SnowflakeUtil;

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
    public void save(User user) {
        user.setId(SnowflakeUtil.nextId());
        LOGGER.info(JSONUtil.parse(user).toString());
    }
}
