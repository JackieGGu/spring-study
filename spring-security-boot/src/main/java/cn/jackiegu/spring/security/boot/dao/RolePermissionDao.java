package cn.jackiegu.spring.security.boot.dao;

import cn.jackiegu.spring.security.boot.model.RolePermissionDO;

import java.util.List;

public interface RolePermissionDao {

    List<RolePermissionDO> findRoleCodeGroupByPermissionUrl();
}
