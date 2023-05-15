package zen.hua.bao.dal.mybatis.biz.multi.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zen.hua.bao.dal.mybatis.biz.multi.broker.ComboTransaction;
import zen.hua.bao.dal.mybatis.biz.multi.common.MultiTransactional;

/**
 * @program: zen-work
 * @description: 多数据源事务切面
 * @author: HUA
 * @create: 2022-12-27 21:12
 **/
@Aspect
@Component
public class MultiTransactionAop {

    private final ComboTransaction comboTransaction;

    @Autowired
    public MultiTransactionAop(ComboTransaction comboTransaction) {
        this.comboTransaction = comboTransaction;
    }

    @Pointcut("@annotation(zen.hua.bao.dal.mybatis.biz.multi.common.MultiTransactional)")
    public void pointCut() {
    }

    @Around("pointCut() && @annotation(multiTransactional)")
    public Object inMultiTransactions(ProceedingJoinPoint pjp, MultiTransactional multiTransactional) {
        return comboTransaction.inCombinedTx(() -> {
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                if (throwable instanceof RuntimeException) {
                    throw (RuntimeException) throwable;
                }
                throw new RuntimeException(throwable);
            }
        }, multiTransactional.value());
    }
}
