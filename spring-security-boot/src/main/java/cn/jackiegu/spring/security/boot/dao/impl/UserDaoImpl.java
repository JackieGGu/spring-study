package cn.jackiegu.spring.security.boot.dao.impl;

import cn.jackiegu.spring.security.boot.dao.UserDao;
import cn.jackiegu.spring.security.boot.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户Dao实现
 *
 * @author JackieGu
 * @date 2021/7/6
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserEntity findByUsername(String username) {
        String sql = "SELECT * FROM spring_security_user WHERE USERNAME = ?";
        List<UserEntity> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserEntity.class), username);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }
}
