package cn.jackiegu.spring.security.boot.service;

import cn.jackiegu.spring.security.boot.model.RolePermissionDO;

import java.util.List;

public interface RolePermissionService {

    List<RolePermissionDO> listPermissionRoles();
}
