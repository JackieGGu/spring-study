package cn.jackiegu.spring.transaction.annotation;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Spring Transaction 纯注解配置类
 *
 * @author JackieGu
 * @date 2021/5/12
 */
@Configuration
@ComponentScan
@EnableTransactionManagement
public class SpringTransactionAnnotationConfiguration {

    @Resource
    private DataSource dataSource;

    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://mysql.jackiegu.cn:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername("test");
        dataSource.setPassword("123$%^");
        dataSource.setInitialSize(3);
        dataSource.setMinIdle(3);
        dataSource.setMaxActive(5);
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getDataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
