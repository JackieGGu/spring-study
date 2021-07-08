package cn.jackiegu.spring.security.boot.dao.impl;

import cn.jackiegu.spring.security.boot.dao.RolePermissionDao;
import cn.jackiegu.spring.security.boot.model.RolePermissionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限Dao实现
 *
 * @author JackieGu
 * @date 2021/7/8
 */
@Repository
public class RolePermissionDaoImpl implements RolePermissionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RolePermissionDO> findRoleCodeGroupByPermissionUrl() {
        String sql = "SELECT PERMISSION_URL, GROUP_CONCAT(ROLE_CODE) ROLE_CODE FROM spring_security_role_permission_view GROUP BY PERMISSION_URL";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RolePermissionDO.class));
    }
}
