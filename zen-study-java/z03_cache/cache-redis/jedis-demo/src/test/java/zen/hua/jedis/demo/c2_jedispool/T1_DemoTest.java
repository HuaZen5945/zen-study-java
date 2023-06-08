package zen.hua.jedis.demo.c2_jedispool;

import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import zen.hua.jedis.demo.BaseTest;
import zen.hua.jedis.demo.service.CoffeeService;

import java.util.Map;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-06-08 23:01
 **/
@Slf4j
public class T1_DemoTest extends BaseTest {
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    @Test
    public void testDemo() {
        log.info(jedisPoolConfig.toString());

        try (Jedis jedis = jedisPool.getResource()) {
            coffeeService.findAllCoffee().forEach(c -> {
                jedis.hset("springbucks-menu",
                        c.getName(),
                        Long.toString(c.getPrice().getAmountMinorLong()));
            });

            Map<String, String> menu = jedis.hgetAll("springbucks-menu");
            log.info("Menu: {}", menu);

            String price = jedis.hget("springbucks-menu", "espresso");
            log.info("espresso - {}",
                    Money.ofMinor(CurrencyUnit.of("CNY"), Long.parseLong(price)));
        }
    }
}
