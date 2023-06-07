package zen.hua.dal.multi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

// 排除数据源的自动配置类
@SpringBootApplication( scanBasePackages = {"zen.hua.bao.dal.mybatis.biz.multi"},
        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@PropertySource(value = {"classpath:biz/multi/application.properties"})
public class MybatisMultiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisMultiApplication.class, args);
    }

}
