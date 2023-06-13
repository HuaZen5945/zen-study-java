package zen.hua.re.ql.sample.c1_base;

import cn.hutool.core.collection.CollUtil;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.Assert;
import org.junit.Test;
import zen.hua.re.ql.sample.model.operator.JoinOperator;

/**
 * @program: zen-study
 * @description: 操作符测试
 * @author: HUA
 * @create: 2023-02-28 22:48
 **/
public class T3_OperatorTest {


    /**
     * 操作符起别名
     *
     * @throws Exception
     */
    @Test
    public void testAlias() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        runner.addOperatorWithAlias("如果", "if", null);
        runner.addOperatorWithAlias("则", "then", null);
        runner.addOperatorWithAlias("否则", "else", null);

        String express = "如果 (语文 + 数学 + 英语 > 270) 则 {return 1;} 否则 {return 0;}";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        Object result = runner.execute(express, context, null, false, false, 5000);
        Assert.assertEquals(1, result);
    }

    /**
     * 将Operator子类添加为新操作符
     *
     * @throws Exception
     */
    @Test
    public void testAddOperator() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        runner.addOperator("join", new JoinOperator());
        Object r = runner.execute("1 join 2 join 3", context, null, false, false);
        // 返回结果 [1, 2, 3]
        Assert.assertEquals(CollUtil.newArrayList(1, 2, 3), r);
    }

    /**
     * 将Operator子类 替换 原有的操作符
     *
     * @throws Exception
     */
    @Test
    public void testReplaceOperator() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        runner.replaceOperator("+", new JoinOperator());
        Object r = runner.execute("1 + 2 + 3", context, null, false, false);
        // 返回结果 [1, 2, 3]
        Assert.assertEquals(CollUtil.newArrayList(1, 2, 3), r);
    }

    /**
     * 测试添加function
     *
     * @throws Exception
     */
    @Test
    public void testAddFunction() throws Exception {
        //(3)addFunction
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        runner.addFunction("join", new JoinOperator());
        Object r = runner.execute("join(1, 2, 3)", context, null, false, false);
        // 返回结果 [1, 2, 3]
        Assert.assertEquals(CollUtil.newArrayList(1, 2, 3), r);
    }

}
