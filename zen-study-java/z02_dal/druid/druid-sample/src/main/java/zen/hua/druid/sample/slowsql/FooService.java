package zen.hua.druid.sample.slowsql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FooService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void selectForUpdate() {
        jdbcTemplate.queryForObject("select id from foo where id = 1 for update", Long.class);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
        }
    }
}
