package zen.hua.re.ql.sample.c1_base;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.Test;
import zen.hua.re.ql.sample.model.function.BeanExample;

/**
 * @program: zen-study
 * @description: 绑定java类或者对象的method
 * @author: HUA
 * @create: 2023-02-28 23:00
 **/
public class T4_addFunOfMethod {

    /**
     * 绑定java类或者对象的method 作为函数
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        // 添加类方法作为函数
        runner.addFunctionOfClassMethod("取绝对值", Math.class.getName(), "abs", new String[]{"double"}, null);
        runner.addFunctionOfClassMethod("转换为大写", BeanExample.class.getName(), "upper", new String[]{"String"}, null);

        // 添加对象方法作为函数
        runner.addFunctionOfServiceMethod("打印", System.out, "println", new String[]{"String"}, null);
        runner.addFunctionOfServiceMethod("contains", new BeanExample(), "anyContains", new Class[]{String.class, String.class}, null);

        String express = "取绝对值(-100); 转换为大写(\"hello world\"); 打印(\"你好吗？\"); contains(\"helloworld\",\"aeiou\")";
        runner.execute(express, new DefaultContext<>(), null, false, false);
    }
}
