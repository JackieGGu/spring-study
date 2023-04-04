package cn.jackiegu.spring.security.boot.dao;

import java.util.List;

public interface UserRoleDao {

    List<String> findRoleCodeByUserId(Long userId);
}
