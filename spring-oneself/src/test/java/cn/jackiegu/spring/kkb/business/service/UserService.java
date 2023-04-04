package cn.jackiegu.spring.kkb.business.service;

import cn.jackiegu.spring.kkb.business.entity.UserEntity;

public interface UserService {

    UserEntity findById(Integer id);
}
