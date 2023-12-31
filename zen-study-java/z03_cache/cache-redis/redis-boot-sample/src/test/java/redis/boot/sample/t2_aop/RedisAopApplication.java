package redis.boot.sample.t2_aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: zen-study
 * @description:
 * @author: HUA
 * @create: 2023-03-13 23:28
 **/
@SpringBootApplication()
public class RedisAopApplication {

    public static final String SCAN_PACKAGE = "redis.boot.sample.t2_aop";

    public static void main(String[] args) {
        SpringApplication.run(RedisAopApplication.class,args);
    }
}
