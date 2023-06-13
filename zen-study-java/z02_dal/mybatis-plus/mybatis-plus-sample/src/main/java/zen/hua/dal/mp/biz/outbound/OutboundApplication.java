package zen.hua.dal.mp.biz.outbound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @program: zen-study
 * @description: outbound启动类
 * @author: HUA
 * @create: 2023-02-11 10:24
 **/
@SpringBootApplication(scanBasePackages = {"zen.hua.dal.mybatis.plus.biz.outbound"},
    exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
// 引入指定properties
// classpath*的使用：
//当项目中有多个classpath路径，并同时加载多个classpath路径下（此种情况多数不会遇到）的文件，*就发挥了作用，
// 如果不加*，则表示仅仅加载第一个classpath路径。
@PropertySource("classpath:biz/outbound/application-outbound.properties")
// 引入spring核心配置文件
@ImportResource({"classpath*:biz/outbound/outbound-spring.xml"})
public class OutboundApplication {
    public static void main(String[] args) {
        SpringApplication.run(OutboundApplication.class,args);
    }
}
