package zen.hua.dal.mp.sample.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    /**
     * exist 是否存在于数据库
     * select 是否进行查询
     * value 对应的字段名
     */
    @TableField(exist = true, select = true,value = "email")
    private String email;
}
