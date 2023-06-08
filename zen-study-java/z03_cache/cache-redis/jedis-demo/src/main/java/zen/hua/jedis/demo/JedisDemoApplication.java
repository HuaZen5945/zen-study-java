package zen.hua.jedis.demo;

import zen.hua.jedis.demo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
public class JedisDemoApplication implements ApplicationRunner {


	public static void main(String[] args) {
		SpringApplication.run(JedisDemoApplication.class, args);
	}



	@Override
	public void run(ApplicationArguments args) throws Exception {

	}
}

