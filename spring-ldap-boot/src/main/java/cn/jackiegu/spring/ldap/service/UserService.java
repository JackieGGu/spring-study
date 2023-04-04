package cn.jackiegu.spring.ldap.service;

/**
 * User Service Interface
 *
 * @author JackieGu
 * @date 2021/11/1
 */
public interface UserService {

    Boolean login(String username, String password);
}
