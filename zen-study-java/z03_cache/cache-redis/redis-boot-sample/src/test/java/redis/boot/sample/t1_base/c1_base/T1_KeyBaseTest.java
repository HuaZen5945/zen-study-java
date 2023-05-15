package redis.boot.sample.t1_base.c1_base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.DataType;
import redis.boot.sample.t1_base.BaseTest;
import redis.boot.sample.biz.RedisBootUtil;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: zen-work
 * @description:
 * @author: HUA
 * @create: 2023-03-15 19:07
 **/
@Slf4j
public class T1_KeyBaseTest extends BaseTest {

    /**
     * <pre>
     *     前期准备:
     *     set k1 v1
     *     set k2 v2
     *     set k3 v3
     *
     * </pre>
     */
    @BeforeEach
    public void beforeEach() {
        Set<String> keys = stringRedisTemplate.keys("*");
        stringRedisTemplate.delete(keys);
        stringRedisTemplate.opsForValue().set("k1", "v1");
        stringRedisTemplate.opsForValue().set("k2", "v2");
        stringRedisTemplate.opsForValue().set("k3", "v3");
    }

    /**
     * 查看当前库所有key
     * shell: keys *
     */
    @Test
    public void keys() {
        log.error("[查看当前库所有key] keys pattern");
        log.error("================== stringRedisTemplate: keys k*");
        Set<String> keys = stringRedisTemplate.keys("k*");
        Assert.assertEquals(keys, CollUtil.newHashSet("k1", "k2", "k3"));

        log.error("================== RedisBootUtil");
        Set<String> keys2 = RedisBootUtil.KeyOps.keysByPattern("k*");
        Assert.assertEquals(keys2, CollUtil.newHashSet("k1", "k2", "k3"));
    }

    /**
     * 查看你的key类型
     * <p>
     * shell: type key
     */
    @Test
    public void type() {
        log.error("[查看你的key类型] type key");
        log.error("================== stringRedisTemplate: type k1");
        DataType type = stringRedisTemplate.type("k1");
        Assert.assertEquals(type, DataType.STRING);

        log.error("================== RedisBootUtil: type k1");
        DataType type2 = RedisBootUtil.KeyOps.type("k1");
        Assert.assertEquals(type2, DataType.STRING);
    }

    /**
     * 删除key
     * shell: del key
     */
    @Test
    public void del() {
        log.error("[删除指定的key数据] del key");
        log.error("================== stringRedisTemplate: del k2");
        Boolean result = stringRedisTemplate.delete("k2");
        Assert.assertEquals(Boolean.TRUE, result);

        log.error("================== RedisBootUtil: del k3");
        Boolean result2 = RedisBootUtil.KeyOps.del("k3");
        Assert.assertEquals(Boolean.TRUE, result2);
    }

    /**
     * 根据value选择非阻塞删除
     * 仅将keys从keyspace元数据中删除，真正的删除会在后续异步操作。
     * shell: unlink key
     */
    @Test
    public void unlink() {
        log.error("[根据value选择非阻塞删除] unlink key");
        log.error("================== stringRedisTemplate: unlink k2");
        Boolean result = stringRedisTemplate.unlink("k2");
        Assert.assertEquals(Boolean.TRUE, result);
        // 非阻塞删除，也是删除，已经查不到了
        String k2V = stringRedisTemplate.opsForValue().get("k2");
        Assert.assertNotEquals("v2",k2V);
        Assert.assertEquals(null,k2V);
    }

    /**
     * 为给定的key设置过期时间
     * expire key n : key的过期时间为n秒
     */
    @Test
    public void expire() {
        log.error("[为给定的key设置过期时间] expire key n : key的过期时间为n秒");
        log.error("================== stringRedisTemplate: expire k2 5");
        Boolean result = stringRedisTemplate.expire("k2",5, TimeUnit.SECONDS);
        Assert.assertEquals(Boolean.TRUE, result);
        String k2V = stringRedisTemplate.opsForValue().get("k2");
        Assert.assertEquals("v2",k2V);
        //  等待过期时间，再次查询
        ThreadUtil.safeSleep(6000);
        String k2V1 = stringRedisTemplate.opsForValue().get("k2");
        Assert.assertEquals(null,k2V1);

        log.error("================== RedisBootUtil: expire k3 5");
        Boolean result2 = RedisBootUtil.KeyOps.expire("k3",5);
        Assert.assertEquals(Boolean.TRUE, result2);
        String k3V = stringRedisTemplate.opsForValue().get("k3");
        Assert.assertEquals("v3",k3V);
        //  等待过期时间，再次查询
        ThreadUtil.safeSleep(6000);
        String k3V1 = stringRedisTemplate.opsForValue().get("k3");
        Assert.assertEquals(null,k3V1);
    }

    /**
     * 查看还有多少秒过期，-1表示永不过期，-2表示已过期
     * ttl key
     */
    @Test
    public void ttl() {
        // 准备： 设置过期数据
        Boolean expireResult = stringRedisTemplate.expire("k1", 10, TimeUnit.SECONDS);
        Assert.assertEquals(Boolean.TRUE, expireResult);

        log.error("[查看还有多少秒过期，-1表示永不过期，-2表示已过期] ttl key");
        log.error("================== stringRedisTemplate: ttl k1");
        Long tllTime1 = stringRedisTemplate.getExpire("k1");
        Assert.assertEquals(Long.valueOf(10l),tllTime1);

        ThreadUtil.safeSleep(1000);
        log.error("================== RedisBootUtil: ttl k1");
        Long tllTime2 = RedisBootUtil.KeyOps.getExpire("k1");
        Assert.assertEquals(Long.valueOf(8l),tllTime2);
    }

    @Test
    public void testMany() {
        log.error("[切换数据库] select");
        log.error("[查看当前数据库的key的数量] dbsize");
        log.error("[清空当前库] flushdb");
        log.error("[通杀全部库] flushall");
    }



}
