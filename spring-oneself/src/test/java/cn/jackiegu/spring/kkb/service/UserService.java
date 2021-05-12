package cn.jackiegu.spring.kkb.service;

import cn.jackiegu.spring.kkb.entity.UserEntity;

public interface UserService {

    UserEntity findById(Integer id);
}
