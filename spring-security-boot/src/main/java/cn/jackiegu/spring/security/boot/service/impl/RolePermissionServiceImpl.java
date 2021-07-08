package cn.jackiegu.spring.security.boot.service.impl;

import cn.jackiegu.spring.security.boot.dao.RolePermissionDao;
import cn.jackiegu.spring.security.boot.model.RolePermissionDO;
import cn.jackiegu.spring.security.boot.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色权限Service实现
 *
 * @author JackieGu
 * @date 2021/7/8
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public List<RolePermissionDO> listPermissionRoles() {
        return rolePermissionDao.findRoleCodeGroupByPermissionUrl();
    }
}
