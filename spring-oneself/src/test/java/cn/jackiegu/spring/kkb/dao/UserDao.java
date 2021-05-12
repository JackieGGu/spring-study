package cn.jackiegu.spring.kkb.dao;

import cn.jackiegu.spring.kkb.entity.UserEntity;

public interface UserDao {

    UserEntity findById(Integer id);
}
