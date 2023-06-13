package zen.hua.re.ql.sample.c1_base;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @program: zen-study
 * @description: 测试集合
 * @author: HUA
 * @create: 2023-02-28 23:15
 **/
public class T8_Collection {

    /**
     * 测试集合的快速创建
     *
     * @throws Exception
     */
    @Test
    public void testCreateCollection() throws Exception {
        ExpressRunner runner = new ExpressRunner(false, false);
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        // 快速创建map
        String express = "abc = NewMap(1:1, 2:2); return abc.get(1) + abc.get(2);";
        Object r = runner.execute(express, context, null, false, false);
        assertEquals(3, r);
        // 快速创建list
        express = "abc = NewList(1, 2, 3); return abc.get(1) + abc.get(2)";
        r = runner.execute(express, context, null, false, false);
        assertEquals(5, r);
        // 快速创建数组
        express = "abc = [1, 2, 3]; return abc[1] + abc[2];";
        r = runner.execute(express, context, null, false, false);
        assertEquals(5, r);
    }

    /**
     * 遍历测试
     *
     * @throws Exception
     */
    @Test
    public void testTraversal() throws Exception {
        String express = "/**遍历map**/\n" +
                "map = new HashMap();\n" +
                "map.put(\"a\", \"a_value\");\n" +
                "map.put(\"b\", \"b_value\");\n" +
                "keySet = map.keySet();\n" +
                "objArr = keySet.toArray();\n" +
                "for (i = 0; i < objArr.length; i++) {\n" +
                "    key = objArr[i];\n" +
                "    System.out.println(map.get(key));\n" +
                "}";
        ExpressRunner runner = new ExpressRunner();
        runner.execute(express, new DefaultContext<>(), null, false, false);
    }
}
