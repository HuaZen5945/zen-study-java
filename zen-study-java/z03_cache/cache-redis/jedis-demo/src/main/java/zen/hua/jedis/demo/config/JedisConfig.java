package zen.hua.jedis.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: zen-study-java
 * @description: jedis配置类
 * @author: HUA
 * @create: 2023-06-08 22:31
 **/
@Configuration
public class JedisConfig {



    @Bean
    @ConfigurationProperties("redis")
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool(@Value("${redis.host}") String host,
                               @Value("${redis.port}") int port,
                               @Value("${redis.password}") String password) {
        JedisPool jedisPool = new JedisPool(jedisPoolConfig(), host,port,2000,password);
        return jedisPool;
    }
}
