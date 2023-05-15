package zen.hua.cache.local.biz.mmc;

import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import zen.hua.cache.local.biz.mmc.enity.PartnerModel;
import zen.hua.cache.local.biz.mmc.use.PartnerBizLocalCache;

import java.util.function.Function;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-05-15 23:32
 **/
@Slf4j
public class BaseTest {


    private static PartnerBizLocalCache partnerBizLocalCache = null;

    @BeforeClass
    public static void before() {
        partnerBizLocalCache = new PartnerBizLocalCache();
    }

    @Test
    public void test() {
        // 执行buildData
        partnerBizLocalCache.get("zhang3", key->buildData());
        // 不执行buildData
        partnerBizLocalCache.get("zhang3", key->buildData());
    }


    public PartnerModel buildData() {
        log.info("[BaseTest#buildData] 执行一次……");
        return new PartnerModel().setId(1l).setName("zhang3");
    }

}
