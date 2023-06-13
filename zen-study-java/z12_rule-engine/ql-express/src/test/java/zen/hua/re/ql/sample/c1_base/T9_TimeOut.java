package zen.hua.re.ql.sample.c1_base;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.exception.QLTimeoutException;
import org.junit.Test;

/**
 * @program: zen-study
 * @description:
 * @author: HUA
 * @create: 2023-02-28 23:23
 **/
public class T9_TimeOut {

    @Test
    public void test() throws Exception {
        try {
            ExpressRunner runner = new ExpressRunner();
            String express = "sum = 0; for(i = 0; i < 1000000000; i++) {sum = sum + i;} return sum;";
            //可通过timeoutMillis参数设置脚本的运行超时时间:1000ms
            Object r = runner.execute(express, new DefaultContext<>(), null, true, false, 1000);
            System.out.println(r);
            throw new Exception("没有捕获到超时异常");
        } catch (QLTimeoutException e) {
            System.out.println(e);
        }
    }
}
