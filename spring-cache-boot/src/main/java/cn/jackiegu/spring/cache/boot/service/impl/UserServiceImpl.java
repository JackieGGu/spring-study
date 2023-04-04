package cn.jackiegu.spring.cache.boot.service.impl;

import cn.jackiegu.spring.cache.boot.model.User;
import cn.jackiegu.spring.cache.boot.service.UserService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 用户 ServiceImpl
 *
 * @author JackieGu
 * @date 2021/9/7
 */
@Service
public class UserServiceImpl implements UserService {

    @CachePut(cacheNames = "users", key = "#name")
    @Override
    public User addUser(String name, Integer age, String sex) {
        return new User(name, age, sex);
    }

    @Cacheable(cacheNames = "users", key = "#name")
    @Override
    public User getUser(String name) {
        return null;
    }
}
