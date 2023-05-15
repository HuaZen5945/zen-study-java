package zen.hua.bao.dal.mybatis.biz.multi.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @program: zen-daily
 * @description: 数据源配置
 * @author: HUA
 * @create: 2022-11-01 21:03
 **/
@Configuration
// 事务生效
@EnableTransactionManagement
// 扫描指定包下的mapper接口，并为其指定sqlSession工厂，多数据源下
//@MapperScan(basePackages = "zen.hua.demo.dao")
@MapperScan(basePackages = HuaDataSourceConfig.BASE_PACKAGE, sqlSessionFactoryRef = "huaSqlSessionFactory")
public class HuaDataSourceConfig {

    private static final String DRIVER_CLZ = "com.mysql.jdbc.Driver";

    public static final String BASE_PACKAGE = "zen.hua.bao.dal.mybatis.biz.multi.dao.huw";

    @Value("${hua.db.url}")
    private String dbUrl;
    @Value("${hua.db.username}")
    private String dbUserName;
    @Value("${hua.db.password}")
    private String dbPassword;

    /**
     * mapper文件路径
     */
    @Value("${hua.mybatis.mapper.location}")
    private Resource[] mapperLocations;
    /**
     * mybatis配置
     */
    @Value("${hua.mybatis.config-location}")
    private Resource configLocation;
    /**
     * 别名的包名
     */
    @Value("${hua.mybatis.typealiases.base.package}")
    private String typeAliasesPackage;

    /**
     * 创建数据源
     * @return
     */
    @Primary
    @Bean(name = "huaDataSource")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(DRIVER_CLZ);
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


    @Primary
    @Bean(name = "huaJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("huaDataSource")DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 创建事务管理器
     * @param dataSource
     * @return
     */
    @Primary
    @Bean(name = "huaTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("huaDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 创建事务模板
     * @param transactionManger
     * @return
     */
    @Primary
    @Bean(name = "huaTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("huaTransactionManager") DataSourceTransactionManager transactionManger) {
        return new TransactionTemplate(transactionManger);
    }

    /**
     * 创建 sqlSessionFactory
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Primary
    @Bean(name = "huaSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("huaDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        // mapper文件位置
        factory.setMapperLocations(mapperLocations);
        factory.setConfigLocation(configLocation);
        factory.setTypeAliasesPackage(typeAliasesPackage);
        return factory.getObject();
    }
}
