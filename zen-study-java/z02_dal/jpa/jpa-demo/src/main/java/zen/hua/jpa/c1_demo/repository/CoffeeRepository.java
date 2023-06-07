package zen.hua.jpa.c1_demo.repository;

import org.springframework.data.repository.CrudRepository;
import zen.hua.jpa.c1_demo.model.Coffee;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
}
