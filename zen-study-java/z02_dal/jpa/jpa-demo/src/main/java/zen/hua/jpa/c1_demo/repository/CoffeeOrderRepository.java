package zen.hua.jpa.c1_demo.repository;

import org.springframework.data.repository.CrudRepository;
import zen.hua.jpa.c1_demo.model.CoffeeOrder;

public interface CoffeeOrderRepository extends CrudRepository<CoffeeOrder, Long> {
}
