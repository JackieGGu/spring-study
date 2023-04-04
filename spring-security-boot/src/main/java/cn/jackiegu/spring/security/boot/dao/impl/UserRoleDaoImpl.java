package cn.jackiegu.spring.security.boot.dao.impl;

import cn.jackiegu.spring.security.boot.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色Dao实现
 *
 * @author JackieGu
 * @date 2021/7/8
 */
@Repository
public class UserRoleDaoImpl implements UserRoleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<String> findRoleCodeByUserId(Long userId) {
        String sql = "SELECT ROLE_CODE FROM spring_security_user_role_view WHERE USER_ID = ?";
        return jdbcTemplate.queryForList(sql, String.class, userId);
    }
}
