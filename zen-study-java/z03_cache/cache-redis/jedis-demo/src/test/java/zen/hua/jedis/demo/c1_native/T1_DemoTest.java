package zen.hua.jedis.demo.c1_native;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import zen.hua.jedis.demo.BaseTest;

import java.util.List;
import java.util.Set;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-06-08 22:41
 **/
public class T1_DemoTest {


    private Jedis buildJedis() {
        //创建Jedis对象, 非线程安全，每次使用每次创建
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.auth("123456");
        return jedis;
    }

    @Test
    public void testDemo() {

        Jedis jedis = buildJedis();

        //添加
        jedis.set("name","lucy");

        //获取
        String name = jedis.get("name");
        Assert.assertEquals("lucy",name);

        //设置多个key-value
        jedis.mset("k1","v1","k2","v2");
        List<String> mget = jedis.mget("k1", "k2");
        System.out.println(mget);

        Set<String> keys = jedis.keys("*");
        for(String key : keys) {
            System.out.println(key);
        }
        jedis.close();
    }


    @Test
    public void testList() {
        //创建Jedis对象
        Jedis jedis = buildJedis();

        jedis.lpush("key1","lucy","mary","jack");
        List<String> values = jedis.lrange("key1", 0, -1);
        System.out.println(values);
        jedis.close();
    }


    @Test
    public void testSet() {
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.44.168",6379);

        jedis.sadd("names","lucy");
        jedis.sadd("names","mary");

        Set<String> names = jedis.smembers("names");
        System.out.println(names);
        jedis.close();
    }

}
