package zen.hua.dal.multi.biz.aop.common;

import java.lang.annotation.*;

/**
 * 多事务注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiTransactional {
 
    String[] value() default {};
}