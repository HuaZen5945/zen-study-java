package zen.hua.spring.cache.redis.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-06-08 23:38
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements Serializable {

    private Long id;

    private String name;
}
