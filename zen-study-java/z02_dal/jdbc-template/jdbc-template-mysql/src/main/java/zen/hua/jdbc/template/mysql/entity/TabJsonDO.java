package zen.hua.jdbc.template.mysql.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TabJsonDO implements Serializable {
    private Long id;

    private String data;
}