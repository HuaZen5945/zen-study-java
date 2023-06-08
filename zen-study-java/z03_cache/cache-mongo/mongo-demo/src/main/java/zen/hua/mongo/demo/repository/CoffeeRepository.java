package zen.hua.mongo.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import zen.hua.mongo.demo.model.Coffee;

import java.util.List;

public interface CoffeeRepository extends MongoRepository<Coffee, String> {
    List<Coffee> findByName(String name);
}
