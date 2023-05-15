package zen.hua.cache.local.biz.mmc.enity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-05-15 23:34
 **/
@Data
@Accessors(chain = true)
public class PartnerModel {

    private Long id;

    private String name;
}
