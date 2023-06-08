package zen.hua.mongo.demo.c1_template;

import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import zen.hua.mongo.demo.BaseTest;
import zen.hua.mongo.demo.model.Coffee;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-06-08 22:11
 **/
@Slf4j
public class T1_BaseTemplateTest extends BaseTest {

    @Test
    public void testDemo() throws Exception {
        // 插入数据
        Coffee espresso = Coffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .createTime(new Date())
                .updateTime(new Date()).build();
        Coffee saved = mongoTemplate.save(espresso);
        log.info("Coffee {}", saved);

        // 查找
        List<Coffee> list = mongoTemplate.find(
                query(Criteria.where("name").is("espresso")), Coffee.class);
        log.info("Find {} Coffee", list.size());
        list.forEach(c -> log.info("Coffee {}", c));


        // 更新
        Thread.sleep(1000); // 为了看更新时间
        UpdateResult result = mongoTemplate.updateFirst(query(where("name").is("espresso")),
                new Update().set("price", Money.ofMajor(CurrencyUnit.of("CNY"), 30))
                        .currentDate("updateTime"),
                Coffee.class);
        log.info("Update Result: {}", result.getModifiedCount());
        Coffee updateOne = mongoTemplate.findById(saved.getId(), Coffee.class);
        log.info("Update Result: {}", updateOne);

        mongoTemplate.remove(updateOne);
    }
}
