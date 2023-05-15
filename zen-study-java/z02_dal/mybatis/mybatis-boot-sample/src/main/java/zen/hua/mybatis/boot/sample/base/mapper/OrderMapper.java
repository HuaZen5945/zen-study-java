package zen.hua.mybatis.boot.sample.base.mapper;

import org.apache.ibatis.annotations.Select;
import zen.hua.mybatis.boot.sample.base.entity.Order;

import java.util.List;

/**
 * @program: zen-work
 * @description: 订单mapper
 * @author: HUA
 * @create: 2023-03-13 21:15
 **/
public interface OrderMapper {

    /**
     * 一对一映射
     *
     * @return
     */
    List<Order> findOneToOne();


    /**
     * 一对一映射
     *
     * @return
     */
    List<Order> findOneToOne2();

    /**
     * 根据uid查询订单列表
     *
     * @param uid
     * @return
     */
    List<Order> findByUid(Long uid);
}
