package zen.hua.dal.mp.sample.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 1. 实体类只需继承 Model 类即可进行强大的 CRUD 操作
 * 2. 需要项目中已注入对应实体的BaseMapper
 * 3. xxById 使用，要么有@TableId注解，要么实现pkVal方法  ？？？
 */
@Data
@Accessors(chain = true)
@TableName("user")
public class UserActiveRecord extends Model<UserActiveRecord> {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;

//    @Override
//    public Serializable pkVal() {
//        return id;
//    }
}
