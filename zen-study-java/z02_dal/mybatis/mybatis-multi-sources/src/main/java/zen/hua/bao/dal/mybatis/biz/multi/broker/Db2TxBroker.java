package zen.hua.bao.dal.mybatis.biz.multi.broker;

import org.springframework.transaction.annotation.Transactional;
import zen.hua.bao.dal.mybatis.biz.multi.common.DbTxConstants;

import java.util.concurrent.Callable;

public class Db2TxBroker {
 
    @Transactional(DbTxConstants.DB2_TX)
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