package zen.hua.mybatis.boot.sample.base.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单表
 */
@Data
public class Order implements Serializable {

    /**
     * id
     */
	private Long id;
    /**
     * 下单时间
     */
    private Date orderTime;
    /**
     * 金额
     */
    private Double amount;
    /**
     * 订单所属用户
     */
    private Consumer consumer;
}