package redis.boot.sample.t1_base.c1_base;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import redis.boot.sample.t1_base.BaseTest;

import java.util.List;

/**
 * @program: zen-work
 * @description:
 * @author: HUA
 * @create: 2023-03-17 23:23
 **/
@Slf4j
public class T3_ListBaseTest extends BaseTest {

    /**
     * <p> 从左边/右边插入一个或多个值。
     * <p> lpush/rpush  <key><value1><value2><value3> .... 	从左边/右边插入一个或多个值。
     */
    @Test
    public void push() {
        log.info("[从左边/右边插入一个或多个值。]  lpush/rpush  <key><value1><value2><value3> ....");
        log.info("=============stringRedisTemplate: lpush k1 v1 v2 v3 ");
        stringRedisTemplate.delete("k1");
        stringRedisTemplate.opsForList().leftPush("k1","v1");
        stringRedisTemplate.opsForList().leftPushAll("k1","v2","v3");
        List<String> stringList = stringRedisTemplate.opsForList().range("k1", 0, -1);
        Assert.assertEquals(CollUtil.newArrayList("v3","v2","v1"),stringList);

        stringRedisTemplate.opsForList().rightPush("k1","v11");
        stringRedisTemplate.opsForList().rightPushAll("k1","v22","v33");
        List<String> stringList2 = stringRedisTemplate.opsForList().range("k1", 0, -1);
        Assert.assertEquals(CollUtil.newArrayList("v3","v2","v1","v11","v22","v33"),stringList2);
    }

    /**
     * 
     */
    @Test
    public void pop() {

    }

}
