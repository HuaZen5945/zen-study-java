package zen.hua.dal.mp.biz.outbound.entity.common;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: zen-work
 * @description: 工作日时间表
 * @author: HUA
 * @create: 2023-02-11 10:31
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("working_day")
public class WorkingDayDO implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    /**
     * 日期
     * 格式:yyyyMMdd
     */
    private Integer ds;

    /**
     * 类型：1 工作日，2非工作日
     */
    private Integer type;
}
