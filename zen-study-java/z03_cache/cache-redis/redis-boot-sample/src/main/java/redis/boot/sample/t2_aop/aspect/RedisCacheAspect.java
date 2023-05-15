package redis.boot.sample.t2_aop.aspect;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import redis.boot.sample.biz.RedisBootUtil;
import redis.boot.sample.t2_aop.anno.RedisCacheAop;
import redis.boot.sample.t2_aop.config.CacheSwitch;

import java.lang.reflect.Method;

/**
 * @program: zenwork
 * @description: 缓存切面
 * todo 改造成动态更新的。
 * @author: HUA
 * @create: 2022-12-22 23:11
 **/
@Slf4j
@Aspect
@Component
public class RedisCacheAspect {


    @Pointcut("@annotation(redis.boot.sample.t2_aop.anno.RedisCacheAop)")
    public void cachePoint() {}

    @Around("cachePoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // redis注解缓存开关
        if (!CacheSwitch.openRedisCache()) {
            return joinPoint.proceed();
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 因为类可能被代理，所以需要真实类的注解
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Method method = targetClass.getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        RedisCacheAop redisCacheAop = method.getAnnotation(RedisCacheAop.class);
        // redis存储的key
        String key = redisCacheAop.key();
        // 没有配置，则使用类路径+方法名+参数来作为key
        if (StrUtil.isBlank(key)) {
            key = targetClass.getName()+"#"+method.getName();
            Object[] args = joinPoint.getArgs();
            if(args != null && args.length>0) {
                key = key + "|" + StrUtil.join("|",args);
            }
        }

        try {
            String methodKey = targetClass.getName()+"#"+method.getName();
            if(CacheSwitch.closeCacheMethods().contains(methodKey)) {
                return joinPoint.proceed();
            }

            // Redis 缓存命中，直接返回
            if(RedisBootUtil.KeyOps.hasKey(key)) {
                return RedisBootUtil.StringOps.get(key);
            }
            // 执行方法
            Object proceed = joinPoint.proceed();
            long expireTime = Long.parseLong(redisCacheAop.expireTime());
            if(null != proceed || redisCacheAop.nullPut()) {
                RedisBootUtil.StringOps.set(key,proceed,expireTime);
            }
            return proceed;
        } catch (Exception e) {
            // 如果redis挂了，也能执行原来的方法。
            log.error("redisCachePoint error key={}",key,e);
            return joinPoint.proceed();
        }
    }

}
