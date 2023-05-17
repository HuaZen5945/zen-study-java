package mysql.c1_curd;

import mysql.MysqlBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;

/**
 * @program: zen-study-java
 * @description: 事务管理测试(手动)
 * @author: HUA
 * @create: 2023-05-17 22:15
 **/
public class T2_TXCurd extends MysqlBaseTest {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * note 多次变更，被覆盖？？？ 后续深入学习
     */
    @Test
    public void testCommit() {
        // 开启事务
        TransactionStatus transactionStatus = beginTransaction();
        //2. 定义sql
        String sql = "insert into user (name) values (?)";
        //3. 执行sql
        int count = jdbcTemplate.update(sql,"郭靖");
        Assert.assertEquals(1,count);
        // 4. 提交事务
        dataSourceTransactionManager.commit(transactionStatus);
    }

    /**
     * 测试回滚
     */
    @Test
    public void testRollback() {
        // 1 开启事务
        TransactionStatus transactionStatus = beginTransaction();
        // 2 编写sql
        String sql = "insert into user (name) values (?)";
        //3. 执行sql
        int count = jdbcTemplate.update(sql,"郭靖");
        Assert.assertEquals(1,count);
        // 4. 回滚事务
        dataSourceTransactionManager.rollback(transactionStatus);
    }

    /**
     * 开启事务
     */
    public TransactionStatus beginTransaction(){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();//事务定义类
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = dataSourceTransactionManager.getTransaction(def);// 返回事务对象
        return status;
    }

}
