package cn.jackiegu.spring.kkb.business.service.impl;

import cn.jackiegu.spring.kkb.business.dao.UserDao;
import cn.jackiegu.spring.kkb.business.entity.UserEntity;
import cn.jackiegu.spring.kkb.business.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Override
    public UserEntity findById(Integer id) {
        return userDao.findById(id);
    }
}
