package zen.hua.mybatis.boot.sample.base.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TabJsonDO implements Serializable {
    private Long id;

    private String data;
}