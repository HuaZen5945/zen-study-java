package zen.hua.dal.multi.biz.broker;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import zen.hua.dal.multi.biz.aop.common.DbTxConstants;

import java.util.concurrent.Callable;

@Component
public class Db1TxBroker {
 
    @Transactional(DbTxConstants.DB1_TX)
    public <V> V inTransaction(Callable<V> callable) {
        try {
            return callable.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}