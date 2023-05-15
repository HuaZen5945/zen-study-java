package zen.hua.cache.local.biz.mmc;

import com.github.benmanes.caffeine.cache.Cache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Function;

/**
 * @program: zenwork
 * @description: 基于caffeine实现
 * @author: HUA
 * @create: 2022-12-22 20:35
 **/
public class BizLocalCache<K,V>  implements BizCache<K,V> {

    private Cache<K,V> cache;

    public BizLocalCache(Cache<K,V> cache) {
        this.cache = cache;
    }


    @Override
    public @Nullable V getIfPresent(@NonNull K key) {
        return cache.getIfPresent(key);
    }

    @Override
    public @Nullable V get(@Nullable K key, @Nullable Function<? super K, ? extends V> fn) {
        return cache.get(key,fn);
    }

    @Override
    public void put(@NonNull K key, @NonNull V value) {
        cache.put(key, value);
    }

    @Override
    public void invalidate(@NonNull K key) {
        cache.invalidate(key);
    }
}
