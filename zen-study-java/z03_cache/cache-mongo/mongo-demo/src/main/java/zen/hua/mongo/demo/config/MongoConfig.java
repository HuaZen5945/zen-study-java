package zen.hua.mongo.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import zen.hua.mongo.demo.converter.MoneyReadConverter;

import java.util.Arrays;

/**
 * @program: zen-study-java
 * @description: mongo配置类
 * @author: HUA
 * @create: 2023-06-08 21:53
 **/
@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        // 配置转换类
        return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
    }
}
