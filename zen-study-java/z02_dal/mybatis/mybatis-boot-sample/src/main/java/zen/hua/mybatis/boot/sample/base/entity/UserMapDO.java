package zen.hua.mybatis.boot.sample.base.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserMapDO implements Serializable {
    private Long id;

    private String name;

    private String sex;

    private String type;
}