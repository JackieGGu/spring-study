package cn.jackiegu.spring.cache.boot.service;

import cn.jackiegu.spring.cache.boot.model.User;

/**
 * 用户 Service
 *
 * @author JackieGu
 * @date 2021/9/7
 */
public interface UserService {

    User addUser(String name, Integer age, String sex);

    User getUser(String name);
}
