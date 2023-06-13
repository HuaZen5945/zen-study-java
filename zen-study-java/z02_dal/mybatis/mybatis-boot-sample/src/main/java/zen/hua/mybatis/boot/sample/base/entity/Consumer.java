package zen.hua.mybatis.boot.sample.base.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: zen-study
 * @description:
 * @author: HUA
 * @create: 2023-03-13 20:56
 **/
@Data
public class Consumer implements Serializable {
    private Long id;
    private String userName;
    private List<Order> orderList;
}
