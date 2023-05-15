package zen.hua.cache.local.biz.mmc.use;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import zen.hua.cache.local.biz.mmc.AbstractCache;
import zen.hua.cache.local.biz.mmc.BizCache;
import zen.hua.cache.local.biz.mmc.BizLocalCache;
import zen.hua.cache.local.biz.mmc.enity.PartnerModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-05-15 23:33
 **/
@Slf4j
public class PartnerBizLocalCache extends AbstractCache<String, PartnerModel> {


    @Override
    protected BizCache<String, PartnerModel> getCache() {
        return new BizLocalCache<>(localCache);
    }

    private final Cache<String, PartnerModel> localCache = Caffeine.newBuilder()
            .expireAfterAccess(2, TimeUnit.HOURS)
            .maximumSize(20)
            .initialCapacity(10)
            .removalListener((key,value,cause) -> {
                log.debug("this value is "+ key +"==="+value + "cause" + cause);
            })
            .recordStats()
            .build();

    public void cleanCache() {
        localCache.invalidateAll();
        localCache.cleanUp();
    }
}
