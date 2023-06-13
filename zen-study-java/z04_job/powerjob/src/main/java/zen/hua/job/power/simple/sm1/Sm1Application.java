package zen.hua.job.power.simple.sm1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @program: zen-work
 * @description: 启动器1
 * @author: HUA
 * @create: 2023-02-10 21:27
 **/
@SpringBootApplication(scanBasePackages = "zen.hua.job.power.simple.biz")
@PropertySource("classpath:simple/application-sm1.properties")
public class Sm1Application {
    public static void main(String[] args) {
        SpringApplication.run(Sm1Application.class, args);
    }
}
