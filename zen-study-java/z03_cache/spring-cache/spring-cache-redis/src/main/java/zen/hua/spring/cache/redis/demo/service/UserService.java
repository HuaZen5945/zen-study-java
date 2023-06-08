package zen.hua.spring.cache.redis.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import zen.hua.spring.cache.redis.demo.model.UserModel;

import java.util.Arrays;
import java.util.List;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-06-08 23:38
 **/

@Slf4j
@Service
@CacheConfig(cacheNames = "name") // 开启缓存，配置缓存name
public class UserService {

    @Cacheable
    public List<UserModel> findAll() {
        log.info("[UserService] 执行了查询");
        UserModel userModel = UserModel.builder().id(1l).name("aaa").build();
        return Arrays.asList(userModel);
    }

    @CacheEvict
    public void reloadCoffee() {
    }

}
