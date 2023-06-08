package zen.hua.spring.cache.redis.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import zen.hua.spring.cache.redis.demo.service.UserService;

import javax.annotation.Resource;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-06-08 23:35
 **/
@Slf4j
@SpringBootApplication
@EnableCaching(proxyTargetClass = true) // 开启基于类的aop代理缓存缓存
public class SpringCacheRedisDemoApplication implements ApplicationRunner {

    @Resource
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SpringCacheRedisDemoApplication.class,args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Count: {}", userService.findAll().size());
        for (int i = 0; i < 5; i++) {
            log.info("Reading from cache.");
            userService.findAll();
        }
        Thread.sleep(5_000); // 等待缓存过期
        log.info("Reading after refresh.");
        userService.findAll().forEach(c -> log.info("Coffee {}", c.getName()));
    }
}
