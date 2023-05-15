package redis.boot.sample.t1_base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.boot.sample.biz.RedisBootUtil;

import javax.annotation.Resource;

/**
 * @program: zen-work
 * @description:
 * @author: HUA
 * @create: 2023-03-15 19:03
 **/
@TestMethodOrder(MethodOrderer.DisplayName.class)
@SpringBootTest(classes = SampleBaseApplication.class)
public class BaseTest {

    @Resource
    protected RedisTemplate<Object,Object> redisTemplate;

    @Resource
    protected StringRedisTemplate stringRedisTemplate;

    @BeforeEach
    public void before() {
        RedisBootUtil.init(redisTemplate,stringRedisTemplate);
    }
}
