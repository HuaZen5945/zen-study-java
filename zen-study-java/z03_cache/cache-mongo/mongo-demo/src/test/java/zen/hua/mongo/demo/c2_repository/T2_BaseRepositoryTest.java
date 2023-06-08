package zen.hua.mongo.demo.c2_repository;

import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import zen.hua.mongo.demo.BaseTest;
import zen.hua.mongo.demo.model.Coffee;
import zen.hua.mongo.demo.repository.CoffeeRepository;

import java.util.Arrays;
import java.util.Date;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-06-08 22:16
 **/
@Slf4j
public class T2_BaseRepositoryTest extends BaseTest {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Test
    public void testDemo() throws Exception {
        Coffee espresso = Coffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .createTime(new Date())
                .updateTime(new Date()).build();
        Coffee latte = Coffee.builder()
                .name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .createTime(new Date())
                .updateTime(new Date()).build();
        // 插入数据
        coffeeRepository.insert(Arrays.asList(espresso, latte));
        // 查询
        coffeeRepository.findAll(Sort.by("name"))
                .forEach(c -> log.info("Saved Coffee {}", c));

        Thread.sleep(1000); // 为看到更新时间
        latte.setPrice(Money.of(CurrencyUnit.of("CNY"), 35.0));
        latte.setUpdateTime(new Date());
        coffeeRepository.save(latte);
        coffeeRepository.findByName("latte")
                .forEach(c -> log.info("Coffee {}", c));
        // 删除数据
        coffeeRepository.deleteAll();
    }
}
