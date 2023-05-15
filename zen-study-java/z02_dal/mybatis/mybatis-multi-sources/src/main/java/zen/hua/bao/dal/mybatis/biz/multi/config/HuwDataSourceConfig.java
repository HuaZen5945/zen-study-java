package zen.hua.bao.dal.mybatis.biz.multi.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @program: zen-work
 * @description: 数据源配置
 * @author: HUA
 * @create: 2022-11-01 21:03
 **/
@Configuration
// 事务生效
@EnableTransactionManagement
// 扫描指定包下的mapper接口，并为其指定sqlSession工厂，多数据源下
//@MapperScan(basePackages = "zen.huw.demo.wdao")
@MapperScan(basePackages = HuwDataSourceConfig.BASE_PACKAGE, sqlSessionFactoryRef = "huwSqlSessionFactory")
public class HuwDataSourceConfig {

    private static final String DRIVER_CLZ = "com.mysql.jdbc.Driver";

    public static final String BASE_PACKAGE = "zen.hua.bao.dal.mybatis.biz.multi.dao.huw";

    @Value("${huw.db.url}")
    private String dbUrl;
    @Value("${huw.db.username}")
    private String dbUserName;
    @Value("${huw.db.password}")
    private String dbPassword;

    /**
     * mapper文件路径
     */
    @Value("${huw.mybatis.mapper.location}")
    private Resource[] mapperLocations;
    /**
     * mybatis配置
     */
    @Value("${huw.mybatis.config-location}")
    private Resource configLocation;
    /**
     * 别名的包名
     */
    @Value("${huw.mybatis.typealiases.base.package}")
    private String typeAliasesPackage;

    /**
     * 创建数据源 * @return
     */
    @Bean(name = "huwDataSource")
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

    /**
     * 创建事务管理器 * @param dataSource * @return
     */
    @Bean(name = "huwTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("huwDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 创建事务模板 * @param transactionManger * @return
     */
    @Bean(name = "huwTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("huwTransactionManager") DataSourceTransactionManager transactionManger) {
        return new TransactionTemplate(transactionManger);
    }

    /**
     * 创建 sqlSessionFactory * @param dataSource * @return * @throws Exception
     */
    @Bean(name = "huwSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("huwDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(mapperLocations);
        factory.setConfigLocation(configLocation);
        factory.setTypeAliasesPackage(typeAliasesPackage);
        return factory.getObject();
    }
}
