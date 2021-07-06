package cn.jackiegu.spring.security.boot.dao;

import cn.jackiegu.spring.security.boot.model.UserEntity;

public interface UserDao {

    UserEntity findByUsername(String username);
}
