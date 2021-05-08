package cn.jackiegu.spring.oneself.service;

import cn.jackiegu.spring.oneself.domain.User;

/**
 * 测试Service
 *
 * @author JackieGu
 * @date 2021/2/3
 */
public interface TestService {

    String hello();

    User sayHi(String name, Integer sex, Integer age);

    String getTime();
}
