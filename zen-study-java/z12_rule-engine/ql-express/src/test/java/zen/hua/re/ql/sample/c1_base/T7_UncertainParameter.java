package zen.hua.re.ql.sample.c1_base;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.DynamicParamsUtil;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @program: zen-study
 * @description: 测试不定参数
 * @author: HUA
 * @create: 2023-02-28 23:12
 **/
public class T7_UncertainParameter {

    @Test
    public void testMethodReplace() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        IExpressContext<String, Object> expressContext = new DefaultContext<String, Object>();
        runner.addFunctionOfServiceMethod("getTemplate", this, "getTemplate", new Class[]{Object[].class}, null);

        //(1)默认的不定参数可以使用数组来代替
        Object r = runner.execute("getTemplate([11,'22', 33L, true])", expressContext, null, false, false);
        assertEquals("11,22,33,true,", r);

        //(2)像java一样,支持函数动态参数调用,需要打开以下全局开关,否则以下调用会失败
        DynamicParamsUtil.supportDynamicParams = true;
        r = runner.execute("getTemplate(11, '22', 33L, true)", expressContext, null, false, false);
        assertEquals("11,22,33,true,", r);
    }


    //等价于getTemplate(Object[] params)
    public Object getTemplate(Object... params) throws Exception {
        String result = "";
        for (Object obj : params) {
            result = result + obj + ",";
        }
        return result;
    }
}
