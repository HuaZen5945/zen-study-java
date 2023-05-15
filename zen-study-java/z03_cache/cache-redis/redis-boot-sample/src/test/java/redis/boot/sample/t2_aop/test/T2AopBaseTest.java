package redis.boot.sample.t2_aop.test;

import cn.hutool.core.collection.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.boot.sample.biz.RedisBootUtil;
import redis.boot.sample.t1_base.SampleBaseApplication;
import redis.boot.sample.t2_aop.RedisAopApplication;
import redis.boot.sample.t2_aop.service.BaseService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-05-15 22:45
 **/
@Slf4j
@TestMethodOrder(MethodOrderer.DisplayName.class)
@SpringBootTest(classes = RedisAopApplication.class)
public class T2AopBaseTest {


    @Resource
    protected RedisTemplate<Object,Object> redisTemplate;

    @Resource
    protected StringRedisTemplate stringRedisTemplate;

    @BeforeEach
    public void before() {
        RedisBootUtil.init(redisTemplate,stringRedisTemplate);
    }

    @Resource
    private BaseService baseService;


    @Test
    public void testAopCache() {
        List<String> list1_1 = baseService.queryList("1");
        List<String> list1_2 = baseService.queryList("1");
        Assert.assertEquals(list1_1, ListUtil.toList("12","13","14"));
        Assert.assertEquals(list1_2, ListUtil.toList("12","13","14"));


        List<String> list2_1 = baseService.queryList("2");
        List<String> list2_2 = baseService.queryList("2");
        Assert.assertEquals(list2_1,ListUtil.toList("22","23","24"));
        Assert.assertEquals(list2_2,ListUtil.toList("22","23","24"));

        List<String> list3_1 = baseService.queryList("3");
        List<String> list3_2 = baseService.queryList("3");
        Assert.assertNull(list3_1);
        Assert.assertNull(list3_2);
    }
}
