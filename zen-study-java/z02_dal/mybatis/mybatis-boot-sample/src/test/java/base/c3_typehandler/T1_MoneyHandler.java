package base.c3_typehandler;

import base.BaseBootTest;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import zen.hua.mybatis.boot.sample.base.entity.Coffee;
import zen.hua.mybatis.boot.sample.base.mapper.CoffeeMapper;

import javax.annotation.Resource;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-06-07 21:56
 * <pre>
 *     1. 配置typehander类
 *     2. 配置typehandler包路径
 * </pre>
 **/
@Slf4j
public class T1_MoneyHandler extends BaseBootTest {

    @Resource
    private CoffeeMapper coffeeMapper;

    @Test
    public void test() {
        Coffee c = Coffee.builder().name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0)).build();
        int count = coffeeMapper.save(c);
        log.info("Save {} Coffee: {}", count, c);

        c = Coffee.builder().name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 25.0)).build();
        count = coffeeMapper.save(c);
        log.info("Save {} Coffee: {}", count, c);

        c = coffeeMapper.findById(c.getId());
        log.info("Find Coffee: {}", c);
    }
}
