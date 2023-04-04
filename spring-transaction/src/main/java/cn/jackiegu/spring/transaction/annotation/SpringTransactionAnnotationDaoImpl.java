package cn.jackiegu.spring.transaction.annotation;

import cn.hutool.core.util.RandomUtil;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class SpringTransactionAnnotationDaoImpl implements SpringTransactionAnnotationDao {

    @Resource
    private DataSource dataSource;

    @Override
    public void save(SpringTransactionAnnotationEntity entity) {
        try {
            Connection connection = this.getConnection();
            String sql = "INSERT INTO spring_user VALUE(?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            entity.setId(Integer.parseInt(1 + RandomUtil.randomNumbers(3)));
            ps.setInt(1, entity.getId());
            ps.setString(2, entity.getName());
            ps.setInt(3, entity.getAge());
            ps.setString(4, entity.getSex());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new SpringTransactionAnnotationException(e);
        }
    }

    @Override
    public void update(SpringTransactionAnnotationEntity entity) {
        try {
            Connection connection = this.getConnection();
            String sql = "UPDATE spring_user SET NAME=?, AGE=?, SEX=? WHERE ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getAge());
            ps.setString(3, entity.getSex());
            ps.setInt(4, entity.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new SpringTransactionAnnotationException(e);
        }
    }

    @Override
    public SpringTransactionAnnotationEntity findById(Integer id) {
        SpringTransactionAnnotationEntity result = null;
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT ID, NAME, AGE, SEX FROM spring_user WHERE ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = SpringTransactionAnnotationEntity.builder()
                    .id(rs.getInt("ID"))
                    .name(rs.getString("NAME"))
                    .age(rs.getInt("AGE"))
                    .sex(rs.getString("SEX"))
                    .build();
            }
            ps.close();
            return result;
        } catch (SQLException e) {
            throw new SpringTransactionAnnotationException(e);
        }
    }

    private Connection getConnection() {
        Map<Object, Object> resources = TransactionSynchronizationManager.getResourceMap();
        ConnectionHolder connectionHolder = (ConnectionHolder) resources.get(dataSource);
        return connectionHolder.getConnection();
    }
}
