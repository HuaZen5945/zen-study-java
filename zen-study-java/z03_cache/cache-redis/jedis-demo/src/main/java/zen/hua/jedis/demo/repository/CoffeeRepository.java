package zen.hua.jedis.demo.repository;

import zen.hua.jedis.demo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
