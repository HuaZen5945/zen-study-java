package zen.hua.dal.mp.sample.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import static zen.hua.dal.mp.sample.base.SampleBaseApplication.BASE_PACKAGE;


@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
@PropertySource("classpath:application-sample-base.properties")
// mapper包扫描
@MapperScan(BASE_PACKAGE + ".mapper")
public class SampleBaseApplication {

    public static final String BASE_PACKAGE = "zen.hua.dal.mp.sample.base";

    public static void main(String[] args) {
        SpringApplication.run(SampleBaseApplication.class, args);
    }
}
