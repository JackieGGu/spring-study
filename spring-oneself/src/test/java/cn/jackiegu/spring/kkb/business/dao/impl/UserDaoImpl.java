package cn.jackiegu.spring.kkb.business.dao.impl;

import cn.jackiegu.spring.kkb.business.dao.UserDao;
import cn.jackiegu.spring.kkb.business.entity.UserEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {

    private DataSource dataSource;

    @Override
    public UserEntity findById(Integer id) {
        UserEntity result = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT ID, NAME, AGE, SEX FROM spring_user WHERE ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = UserEntity.builder()
                    .id(rs.getInt("ID"))
                    .name(rs.getString("NAME"))
                    .age(rs.getInt("AGE"))
                    .sex(rs.getString("SEX"))
                    .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
