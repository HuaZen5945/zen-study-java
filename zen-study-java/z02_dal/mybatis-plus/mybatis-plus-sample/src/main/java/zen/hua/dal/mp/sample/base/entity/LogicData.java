package zen.hua.dal.mp.sample.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @program: zen-study
 * @description: 逻辑删除entity
 * @author: HUA
 * @create: 2023-02-23 23:56
 **/
@Data
@Accessors(chain = true)
@TableName("logic_data")
public class LogicData {
    private Long id;
    private String name;
    /**
     * 删除标识
     * 1. 3.3.0 之后，配置全局的话，就无需另外配置
     * 2. 一个类中只能有一个 TableLogic
     * // SET deleted=1 WHERE deleted=0
     * // WHERE AND deleted=0 ……
     */
//    @TableLogic(value = "0", delval = "1")
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    /**
     * 逻辑删除时间
     * // SET delete_time=now() WHERE delete_time IS NULL
     * // WHERE delete_time IS NULL
     */
    @TableLogic(value = "null", delval = "now()")
    private Date deleteTime;
}
