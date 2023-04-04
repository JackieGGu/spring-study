package cn.jackiegu.spring.kkb.business.dao;

import cn.jackiegu.spring.kkb.business.entity.UserEntity;

public interface UserDao {

    UserEntity findById(Integer id);
}
