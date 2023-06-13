package redis.boot.sample.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: zen-study
 * @description:
 * @author: HUA
 * @create: 2023-03-13 23:26
 **/
@SpringBootApplication()
public class SampleLockApplication {
    public static void main(String[] args) {
        SpringApplication.run(SampleLockApplication.class,args);
    }
}
