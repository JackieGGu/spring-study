package cn.jackiegu.spring.kkb.service.impl;

import cn.jackiegu.spring.kkb.dao.UserDao;
import cn.jackiegu.spring.kkb.entity.UserEntity;
import cn.jackiegu.spring.kkb.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Override
    public UserEntity findById(Integer id) {
        return userDao.findById(id);
    }
}
