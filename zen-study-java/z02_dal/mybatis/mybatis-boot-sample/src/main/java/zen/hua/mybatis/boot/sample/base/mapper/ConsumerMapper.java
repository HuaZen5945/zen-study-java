package zen.hua.mybatis.boot.sample.base.mapper;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import zen.hua.mybatis.boot.sample.base.entity.Consumer;

import java.util.List;

/**
 * @program: zen-work
 * @description: 消费者mapper
 * @author: HUA
 * @create: 2023-03-13 20:59
 **/
public interface ConsumerMapper {

    /**
     * 测试一对多
     * @return
     */
    List<Consumer> findOneToMany();

    /**
     * 测试一对多,两个sql
     * @return
     */
    List<Consumer> findOneToMany2();

    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    Consumer findById(Long id);
}
