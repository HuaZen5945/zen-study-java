package zen.hua.re.ql.sample.c1_base;

import com.ql.util.express.ExpressRunner;
import org.junit.Test;

/**
 * @program: zen-study
 * @description: 编译脚本测试
 * @author: HUA
 * @create: 2023-02-28 23:10
 **/
public class T6_CompileScript {


    /**
     * 有int：
     * var : 数学
     * var : 综合考试
     * var : 英语
     * var : 语文
     * <p>
     * 无int： 多了个平均分
     * var : 平均分
     * var : 数学
     * var : 综合考试
     * var : 英语
     * var : 语文
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
//        String express = "int 平均分 = (语文 + 数学 + 英语 + 综合考试.科目2) / 4.0; return 平均分";
        String express = "平均分 = (语文 + 数学 + 英语 + 综合考试.科目2) / 4.0; return 平均分";
        ExpressRunner runner = new ExpressRunner(true, true);
        String[] names = runner.getOutVarNames(express);
        for (String s : names) {
            System.out.println("var : " + s);
        }
    }
}
