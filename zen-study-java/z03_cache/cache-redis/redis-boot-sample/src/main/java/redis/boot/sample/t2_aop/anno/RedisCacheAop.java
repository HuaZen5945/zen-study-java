package redis.boot.sample.t2_aop.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hua
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheAop {

    /**
     * key 指定key
     * @return
     */
    String key() default "";

    /**
     * 过期时间，单位：秒
     * 默认一小时
     * @return
     */
    String expireTime() default "3600";

    /**
     * 空值是否写入缓存
     * @return
     */
    boolean nullPut() default true;
}
