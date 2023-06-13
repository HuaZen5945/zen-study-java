package zen.hua.mybatis.boot.sample.base.mapper;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import zen.hua.mybatis.boot.sample.base.entity.Consumer;
import zen.hua.mybatis.boot.sample.base.entity.Order;

import java.util.List;

/**
 * @program: zen-study
 * @description: 订单注解mapper
 * @author: HUA
 * @create: 2023-03-13 21:15
 **/
public interface OrderAnnoMapper {

    /**
     * 一对一映射
     * @return
     */
    @Select("select o.id, o.order_time, c.id as consumer_id,\n" +
            "               c.user_name as consumer_user_name\n" +
            "        from `order` o left join `consumer` c on o.user_id= c.id")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "orderTime",column = "order_time"),
            @Result(property = "amount",column = "amount"),
//            @Result(property = "consumer", javaType = Consumer.class,
//                    one = @One(columnPrefix = "consumer_"))
    })
    List<Order> findOneToOne();


    @Select("select o.id, o.order_time,o.user_id uid from `order` o ")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "orderTime",column = "order_time"),
            @Result(property = "amount",column = "amount"),
            @Result(property = "consumer", column = "uid", javaType = Consumer.class,
                    // 使用sql, 每个sql分别查询
                    one = @One(select = "zen.hua.mybatis.boot.sample.base.mapper.ConsumerAnnoMapper.findById"))
    })
    List<Order> findOneToOne2();

    /**
     * 根据uid查询订单列表
     *
     * @param uid
     * @return
     */
    @Select("select * from `order` where user_id = #{uid}")
    List<Order> findByUid(Long uid);
}
