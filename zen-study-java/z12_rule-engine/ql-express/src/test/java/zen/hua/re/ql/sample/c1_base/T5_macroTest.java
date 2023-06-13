package zen.hua.re.ql.sample.c1_base;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @program: zen-study
 * @description: macro宏定义测试
 * @author: HUA
 * @create: 2023-02-28 23:07
 **/
public class T5_macroTest {


    @Test
    public void test() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        runner.addMacro("计算平均成绩", "(语文+数学+英语)/3.0");
        runner.addMacro("是否优秀", "计算平均成绩>90");
        IExpressContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("语文", 88);
        context.put("数学", 99);
        context.put("英语", 95);
        Object result = runner.execute("是否优秀", context, null, false, false);
        // 返回结果true
        assertEquals(result, true);
    }
}
