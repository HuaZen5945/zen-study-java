package zen.hua.mybatis.boot.sample.base.mapper;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import zen.hua.mybatis.boot.sample.base.entity.Consumer;

import java.util.List;

/**
 * @program: zen-work
 * @description: 消费者注解式mapper
 * @author: HUA
 * @create: 2023-03-13 20:59
 **/
public interface ConsumerAnnoMapper {

    /**
     * 测试一对多
     * @return
     */
    @Select("select * from consumer")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "userName",column = "user_name"),
            @Result(property = "orderList",column = "id",javaType = List.class,
                    many = @Many(select = "zen.hua.mybatis.boot.sample.base.mapper.OrderAnnoMapper.findByUid"))
    })
    List<Consumer> findOneToMany();

    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    @Select("select * from consumer where id = #{id}")
    Consumer findById(Long id);
}
