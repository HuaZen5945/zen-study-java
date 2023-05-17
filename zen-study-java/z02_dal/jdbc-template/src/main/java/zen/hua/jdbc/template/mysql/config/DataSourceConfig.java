package zen.hua.jdbc.template.mysql.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @program: zen-study-java
 * @description: 数据源配置
 * @author: HUA
 * @create: 2023-05-17 08:23
 **/
@Configuration
public class DataSourceConfig {


    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.username}")
    private String dbUserName;

    @Value("${db.password}")
    private String dbPassword;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUserName);
        dataSource.setPassword(dbPassword);
        dataSource.setMaxActive(80);
        dataSource.setMaxWait(5000L);
        dataSource.setRemoveAbandoned(true);
        dataSource.setLogAbandoned(true);
        dataSource.setRemoveAbandonedTimeoutMillis(1000 * 60 * 5);
        return dataSource;
    }

    // 需要准备好数据源
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 装配事务管理器
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
