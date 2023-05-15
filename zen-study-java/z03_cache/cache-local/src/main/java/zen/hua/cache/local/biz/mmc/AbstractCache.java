package zen.hua.cache.local.biz.mmc;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;

/**
 * @program: zenwork
 * @description: 抽象缓存类
 * @author: HUA
 * @create: 2022-12-22 19:42
 **/
@Slf4j
public abstract class AbstractCache<K,V> {

    /**
     * 获取不同的缓存实现
     * @return
     */
    protected abstract BizCache<K,V> getCache();

    /**
     * 根据key获取缓存，获取不到，则调用fn，存放并返回。
     * @param key
     * @param queryFn
     * @return
     */
    public V getAndPut(@NonNull K key, Function<K,V> queryFn) {
        V value = getCache().getIfPresent(key);
        if (value == null) {
            value = queryFn.apply(key);
            put(key,value);
        }
        return value;
    }

    public V get(@NonNull K key, Function<K,V> queryFn) {
        return getCache().get(key,queryFn);
    }

    public K put(@NonNull K key, V value) {
        if(null != value) {
            getCache().put(key,value);
            return key;
        }
        return null;
    }

    public void remove(@NonNull K key) {
        getCache().invalidate(key);
    }

    public V getIfPresent(@NonNull K key) {
        return getCache().getIfPresent(key);
    }
}
