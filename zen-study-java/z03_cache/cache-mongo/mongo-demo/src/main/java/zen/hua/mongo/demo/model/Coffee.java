package zen.hua.mongo.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coffee {
    @Id // spring : String -> mongo的ObjectId
    private String id;
    private String name;
    private Money price;
    private Date createTime;
    private Date updateTime;
}
