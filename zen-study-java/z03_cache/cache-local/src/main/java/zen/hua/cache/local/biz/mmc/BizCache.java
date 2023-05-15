package zen.hua.cache.local.biz.mmc;

import com.google.errorprone.annotations.CompatibleWith;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Function;

/**
 * @program: zenwork
 * @description: 抽象缓存接口，用户自定义不同实现
 * @author: HUA
 * @create: 2022-12-22 19:43
 **/
public interface BizCache<K,V> {

    /**
     * 从现有的缓存中获取，如果缓存中有key，则返回value，如果没有则返回null
     * @param key
     * @return
     */
    @Nullable V getIfPresent(@CompatibleWith("K") @NonNull K key);

    /**
     * 从现有的缓存中获取，如果缓存中有key，则返回value，如果没有则返回fn执行结果
     * @param key
     * @param fn
     * @return
     */
    @Nullable V get(@Nullable K key, @Nullable Function<? super K, ? extends  V> fn);

    /**
     * 存放 key-value到缓存中
     * @param key
     * @param value
     */
    void put(@NonNull K key, @NonNull V value);

    /**
     * 使key无效
     * @param key
     */
    void invalidate(@CompatibleWith("K") @NonNull K key);
}
