package base.c3_result;

import base.BaseBootTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.mybatis.boot.sample.base.entity.Consumer;
import zen.hua.mybatis.boot.sample.base.entity.Order;
import zen.hua.mybatis.boot.sample.base.mapper.ConsumerAnnoMapper;
import zen.hua.mybatis.boot.sample.base.mapper.ConsumerMapper;
import zen.hua.mybatis.boot.sample.base.mapper.OrderAnnoMapper;
import zen.hua.mybatis.boot.sample.base.mapper.OrderMapper;

import java.util.List;

/**
 * @program: zen-study
 * @description: 手动设置
 * @author: HUA
 * @create: 2023-03-13 21:34
 **/
@Slf4j
public class T1_Result_Map extends BaseBootTest {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private ConsumerAnnoMapper consumerAnnoMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderAnnoMapper orderAnnoMapper;

    /**
     * 测试一对一映射
     */
    @Test
    public void testOneToOne() {
        // xml方式测试
        List<Order> orderList = orderMapper.findOneToOne();
        Assert.assertNotNull(orderList);
        for (Order order : orderList) {
            System.out.println(order.getId()+": "+order);
            Assert.assertNotNull(order.getConsumer());
        }

        // 注解方式测试
        List<Order> orderList2 = orderAnnoMapper.findOneToOne2();
        Assert.assertNotNull(orderList2);
        for (Order order : orderList2) {
            System.out.println(order.getId()+": "+order);
            Assert.assertNotNull(order.getConsumer());
        }
    }

    /**
     * 测试一对一映射
     * 两次sql的方式
     */
    @Test
    public void testOneToOne2() {
        // xml方式测试
        log.error("================= xml方式，两次sql==============");
        List<Order> orderList = orderMapper.findOneToOne2();
        Assert.assertNotNull(orderList);
        for (Order order : orderList) {
            System.out.println(order.getId()+": "+order);
            Assert.assertNotNull(order.getConsumer());
        }

        log.error("================= 注解方式，两次sql==============");
        // 注解方式测试
        List<Order> orderList2 = orderAnnoMapper.findOneToOne2();
        Assert.assertNotNull(orderList2);
        for (Order order : orderList2) {
            System.out.println(order.getId()+": "+order);
            Assert.assertNotNull(order.getConsumer());
        }
    }

    /**
     * 测试一对多映射
     */
    @Test
    public void testOneToMany() {
        // xml方式测试
        log.error("================= xml方式，两次sql==============");
        List<Consumer> consumerList = consumerMapper.findOneToMany2();
        Assert.assertNotNull(consumerList);
        for (Consumer consumer : consumerList) {
            System.out.println(consumer.getId()+": "+consumer);
            Assert.assertNotNull(consumer.getOrderList());
        }

        // 注解方式测试
        log.error("================= 注解方式，两次sql==============");
        List<Consumer> consumerList1 = consumerAnnoMapper.findOneToMany();
        Assert.assertNotNull(consumerList1);
        for (Consumer consumer : consumerList1) {
            System.out.println(consumer.getId()+": "+consumer);
            Assert.assertNotNull(consumer.getOrderList());
        }
    }

    /**
     * 测试一对多映射
     */
    @Test
    public void testOneToMany2() {
        // xml方式测试

        List<Consumer> consumerList = consumerMapper.findOneToMany2();
        Assert.assertNotNull(consumerList);
        for (Consumer consumer : consumerList) {
            System.out.println(consumer.getId()+": "+consumer);
            Assert.assertNotNull(consumer.getOrderList());
        }

        // 注解方式测试
        List<Consumer> consumerList1 = consumerAnnoMapper.findOneToMany();
        Assert.assertNotNull(consumerList1);
        for (Consumer consumer : consumerList1) {
            System.out.println(consumer.getId()+": "+consumer);
            Assert.assertNotNull(consumer.getOrderList());
        }
    }

    /**
     * 测试多对多映射
     */
    @Test
    public void testManyToMany() {
        // todo 待补充
    }
}
