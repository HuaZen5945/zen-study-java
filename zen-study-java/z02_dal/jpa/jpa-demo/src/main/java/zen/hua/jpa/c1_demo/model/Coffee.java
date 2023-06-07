package zen.hua.jpa.c1_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // 告诉jpa  这是一个实体类 需要跟数据库的表做映射
@Table(name = "T_MENU") //建立实体类和数据表关
public class Coffee implements Serializable {
    @Id // 主键
    @GeneratedValue // 自动生成策略， 默认序列号
    private Long id;
    private String name;
    @Column // 处理为 decimal(19.2)
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;
    @Column(updatable = false) //类的属性和实体类的字段关系
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date updateTime;
}
