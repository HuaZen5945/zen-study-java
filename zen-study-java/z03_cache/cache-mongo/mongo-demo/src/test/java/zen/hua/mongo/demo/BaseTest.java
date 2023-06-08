package zen.hua.mongo.demo;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoDemoApplication.class)
public class BaseTest {


    @Autowired
    protected MongoTemplate mongoTemplate;

}

