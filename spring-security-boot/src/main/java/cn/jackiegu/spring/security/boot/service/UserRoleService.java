package cn.jackiegu.spring.security.boot.service;

import java.util.List;

public interface UserRoleService {

    List<String> listUserRoleCodes(Integer userId);
}
