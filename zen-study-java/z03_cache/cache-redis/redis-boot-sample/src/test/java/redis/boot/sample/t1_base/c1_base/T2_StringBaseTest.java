package redis.boot.sample.t1_base.c1_base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import redis.boot.sample.t1_base.BaseTest;
import redis.boot.sample.biz.RedisBootUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: zen-work
 * @description:
 * @author: HUA
 * @create: 2023-03-15 23:48
 **/
@Slf4j
public class T2_StringBaseTest extends BaseTest {

    /**
     * 删除与获取
     */
    @Test
    public void set() {
        log.error("[添加键值对] set  <key> <value>");
        log.error("================== stringRedisTemplate: set k11 v11");
        stringRedisTemplate.opsForValue().set("k11","v11");
        Assert.assertEquals(stringRedisTemplate.opsForValue().get("k11"), "v11");

        log.error("================== RedisBootUtil: ");
        RedisBootUtil.StringOps.set("k22","v22");
        Assert.assertEquals(RedisBootUtil.StringOps.get("k22"),"v22");
    }

    /**
     * 将给定的<value> 追加到原值的末尾
     */
    @Test
    public void append() {
        log.error("[将给定的<value> 追加到原值的末尾] append  <key> <value>");
        log.error("================== stringRedisTemplate: append k12 aa");
        stringRedisTemplate.opsForValue().set("k12","v12");
        stringRedisTemplate.opsForValue().append("k12","aa");
        Assert.assertEquals("v12aa",stringRedisTemplate.opsForValue().get("k12"));
    }

    /**
     * 获得值的长度
     */
    @Test
    public void strLen() {
        log.error("[获得值的长度] strlen  <key>");
        log.error("================== stringRedisTemplate: strlen k31");
        stringRedisTemplate.opsForValue().set("k31","v31");
        Long size = stringRedisTemplate.opsForValue().size("k31");
        Assert.assertEquals(Long.valueOf(3),size);
    }

    /**
     * 只有在 key 不存在时 , 设置 key 的值
     */
    @Test
    public void setNX() {
        log.error("[只有在 key 不存在时, 设置 key 的值] setnx  <key><value>");
        log.error("================== stringRedisTemplate: setnx k41 v42");
        stringRedisTemplate.delete("k41");
        Boolean r1 = stringRedisTemplate.opsForValue().setIfAbsent("k41", "v41");
        Boolean r2 = stringRedisTemplate.opsForValue().setIfAbsent("k41", "v42");
        String value = stringRedisTemplate.opsForValue().get("k41");
        Assert.assertEquals(true,r1);
        Assert.assertEquals(false,r2);
        Assert.assertEquals("v41",value);
    }

    /**
     * <p>
     *     设置键值的同时，设置过期时间，单位秒
     * </p>
     * <p>
     *     setex  <key><过期时间><value>
     * </p>
     */
    @Test
    public void setEx() {
        log.error("[设置键值的同时，设置过期时间，单位秒] setex  <key><过期时间><value>");
        log.error("================== stringRedisTemplate: setnx k1 5 v1");
        stringRedisTemplate.delete("k1");
        Boolean r1 = stringRedisTemplate.opsForValue().setIfAbsent("k1", "v1",5, TimeUnit.SECONDS);
        Boolean r2 = stringRedisTemplate.opsForValue().setIfAbsent("k1", "v1");
        Assert.assertEquals(true,r1);
        Assert.assertEquals(false,r2);
        // 等待过期
        ThreadUtil.safeSleep(6000);
        Boolean r3 = stringRedisTemplate.opsForValue().setIfAbsent("k1", "v1");
        Assert.assertEquals(true,r3);
    }

    /**
     * <pre>
     *     以新换旧，设置了新值同时获得旧值
     *     getset <key><value>
     * </pre>
     */
    @Test
    public void getSet() {
        log.error("[以新换旧，设置了新值同时获得旧值] getset <key><value>");
        log.error("================== stringRedisTemplate: getset name jack");
        stringRedisTemplate.opsForValue().set("name","lucy");
        String oldValue = stringRedisTemplate.opsForValue().getAndSet("name","jack");
        String newValue = stringRedisTemplate.opsForValue().get("name");
        Assert.assertEquals("lucy",oldValue);
        Assert.assertEquals("jack",newValue);
    }

    /**
     * <p>
     * 将 key 中储存的数字值增1。
     * 只能对数字值操作，如果为空，新增值为1
     * </p>
     * <p>
     * 将 key 中储存的数字值增减。自定义步长
     * </p>
     */
    @Test
    public void incr() {
        log.error("[将 key 中储存的数字值增1] incr  <key>");
        log.error("================== stringRedisTemplate: incr k51");
        stringRedisTemplate.delete("k51");
        Long r1 = stringRedisTemplate.opsForValue().increment("k51");
        Assert.assertEquals(Long.valueOf(1),r1);
        Long r2 = stringRedisTemplate.opsForValue().increment("k51");
        Assert.assertEquals(Long.valueOf(2),r2);

        log.error("[将 key 中储存的数字值增减。自定义步长] incrby  <key> <步长>");
        log.error("================== stringRedisTemplate: incrby k51 3");
        Long r3 = stringRedisTemplate.opsForValue().increment("k51",3l);
        Assert.assertEquals(Long.valueOf(5),r3);
    }


    /**
     * <p>将 key 中储存的数字值减1
     * <p>只能对数字值操作，如果为空，新增值为-1
     * <p>将 key 中储存的数字值增减。自定义步长。</p>
     */
    @Test
    public void decr() {
        log.error("[将 key 中储存的数字值增1] decr  <key>");
        log.error("================== stringRedisTemplate: decr k61");
        stringRedisTemplate.delete("k61");
        Long r1 = stringRedisTemplate.opsForValue().decrement("k61");
        // 如果为空，新增值为-1
        Assert.assertEquals(Long.valueOf(-1),r1);

        Long r2 = stringRedisTemplate.opsForValue().decrement("k61");
        Assert.assertEquals(Long.valueOf(-2),r2);

        log.error("[减少 key 中储存的数字值。自定义步长。] decrby <key> <步长> ");
        log.error("================== stringRedisTemplate: decrby k61 3");
        Long r3 = stringRedisTemplate.opsForValue().decrement("k61",3l);
        Assert.assertEquals(Long.valueOf(-5),r3);
    }

    /**
     * <p>
     *     同时设置一个或多个 key-value对
     * </p>
     */
    @Test
    public void mSet() {
        log.error("[同时设置一个或多个 key-value对] mset  <key1><value1><key2><value2>  ..... ");
        log.error("================== stringRedisTemplate: mset k1 v1 k2 v2 k3 v3");
        Map<String, String> paramMap = MapUtil.ofEntries(
                MapUtil.entry("k1", "v1"),
                MapUtil.entry("k2", "v2"),
                MapUtil.entry("k3", "v3")
        );
        stringRedisTemplate.opsForValue().multiSet(paramMap);

        log.error("[同时获取一个或多个 value ] mget  <key1><key2><key3> ..... ");
        log.error("================== stringRedisTemplate: mget k1 k2 k3 ");
        List<String> valueList = stringRedisTemplate.opsForValue().multiGet(CollUtil.newArrayList("k1", "k2", "k3"));
        Assert.assertEquals(CollUtil.newArrayList("v1","v2","v3"),valueList);
    }

    /**
     * <p>
     *   同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
     * </p>
     * <P>
     *   原子性，有一个失败则都失败
     * </P>
     *
     */
    @Test
    public void mSetNX() {
        log.error("[同时设置一个或多个 key-value对] mset  <key1><value1><key2><value2>  ..... ");
        log.error("================== stringRedisTemplate: msetnx k1 v1 82 v2 k3 v3");
        stringRedisTemplate.delete(CollUtil.newArrayList("k1","k2","k3"));
        Map<String, String> paramMap = MapUtil.ofEntries(
                MapUtil.entry("k1", "v1"),
                MapUtil.entry("k2", "v2"),
                MapUtil.entry("k3", "v3")
        );
        Boolean r1 = stringRedisTemplate.opsForValue().multiSetIfAbsent(paramMap);
        Assert.assertEquals(true,r1);


        stringRedisTemplate.delete(CollUtil.newArrayList("k1","k2","k3"));
        log.error("[原子性，有一个失败则都失败 ] ");
        stringRedisTemplate.opsForValue().set("k1","v1");
        Boolean r2 = stringRedisTemplate.opsForValue().multiSetIfAbsent(paramMap);
        Assert.assertEquals(false,r2);
    }


    /**
     * <p>
     *     获得值的范围，类似java中的substring，前包，后包
     * </p>
     * <p>
     *      getrange  <key><起始位置><结束位置>
     * </p>
     */
    @Test
    public void getRange() {
        log.error("[获得值的范围，类似java中的substring，前包，后包] getrange  <key><起始位置><结束位置> ");
        log.error("================== stringRedisTemplate: getrange name 0 3");
        stringRedisTemplate.opsForValue().set("name","lucymary");
        String value = stringRedisTemplate.opsForValue().get("name", 0, 3);
        Assert.assertEquals("lucy",value);
    }

    /**
     * <p>
     *     用 <value>  覆写<key>所储存的字符串值，从<起始位置>开始(索引从0开始)。
     * </p>
     *
     * <p>
     *     setrange  <key><起始位置><value>
     * </p>
     */
    @Test
    public void setRange() {
        log.error("[用 <value>  覆写<key>所储存的字符串值，从<起始位置>开始(索引从0开始)。] setrange  <key><起始位置><value> ");
        log.error("================== stringRedisTemplate: getrange name 0 3");
        stringRedisTemplate.opsForValue().set("name","lucymary");
        stringRedisTemplate.opsForValue().set("name", "abc",3);
        Assert.assertEquals("lucabcry",stringRedisTemplate.opsForValue().get("name"));
    }


    @Test
    public void testMany() {
        log.info("[*NX：当数据库中key不存在时，可以将key-value添加数据库]");
        log.info("[*XX：当数据库中key存在时，可以将key-value添加数据库，与NX参数互斥]");
        log.info("[*EX：key的超时秒数]");
        log.info("[*PX：key的超时毫秒数，与EX互斥]");
    }
}
