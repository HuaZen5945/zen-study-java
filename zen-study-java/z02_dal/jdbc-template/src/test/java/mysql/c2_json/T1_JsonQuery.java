package mysql.c2_json;

import mysql.MysqlBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;

/**
 * @program: zen-study-java
 * @description:
 * @author: HUA
 * @create: 2023-05-17 22:42
 *
  <pre>
    JSON_EXTRACT函数
    json_extract(jsonDoc,'path')
    ● jsonDoc，要么是json字符串，要么是json字段。
    ● 通过$加.json对象中的字段取出 json对象中的value
    ● 通过$加[i]取出 json数据中索引i处的value
    ● 其他语法JSON_EXTRACT(jsonDoc -> '$[0]', '$.path' );
    举例：
    ● ‘$.*’ 返回全部json
    ● ‘$.title’ 返回key=”title”的数据
    ● ‘$**.text’ 返回所有最底层key=”text”的数据
    ● ‘$.content[*].item1[*]’ 返回key=content的list的key=item1的list的所有内容
  </pre>
 **/
public class T1_JsonQuery extends MysqlBaseTest {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 普通json的key
     * insert into tab_json values (1,
     * '{"k1": 12, "k2": 34, "k3": {"L1": {"M1":1,"M2":2}, "L2": "value2"}}');
     * insert into tab_json VALUES (2,
     * '{"success": true,"code": "0","message": "",
     * "data": {"name": "jerry","age": "18","sex": "男"}}');
     *
     */
    @Test
    public void test1() {
        String sql1 = "select id from tab_json where JSON_EXTRACT(data,'$.k1') = 12";
        long result1 = jdbcTemplate.queryForObject(sql1, Long.class);
        Assert.assertEquals(1l, result1);

        String sql2 = "select id from tab_json where JSON_EXTRACT(data, '$.k3.L2') = 'value2'";
        long result2 = jdbcTemplate.queryForObject(sql2, Long.class);
        Assert.assertEquals(1l, result2);

        String sql3 = "select id from tab_json where JSON_EXTRACT(data, '$.k3.L1.M1') = 1";
        long result3 = jdbcTemplate.queryForObject(sql3, Long.class);
        Assert.assertEquals(1l, result3);
    }


    /**
     * 场景2: 特殊字符使用 ""
     * insert into tab_json VALUES (3, '{"1": true,"l-l": "0"}');
     */
    @Test
    public void test2() {
        // 1 被解析成数字，而非字符
        String sql1 = "select id from tab_json where json_extract(data,'$.1') = true";
        // SQLSyntaxErrorException: Invalid JSON path expression
//        long result1 = jdbcTemplate.queryForObject(sql1, Long.class);
//        Assert.assertEquals(3l, result1);

        String sql2 = "select id from tab_json where json_extract(data,'$.\"1\"') = true";
        long result2 = jdbcTemplate.queryForObject(sql2, Long.class);
        Assert.assertEquals(3l, result2);
    }

    /**
     * insert into tab_json VALUES (4,
     *     '{"success": true
     *     "data": [{"name": "jerry","age": "18"},{"name": "jack","age": "20"}]}');
     * insert into tab_json VALUES (5, '{"success": true,"data": [1,2,3,4,5]}');
     */
    @Test
    public void test3() {
        // -- 方法1: list类型数据
        String sql1 = "select id from tab_json where JSON_EXTRACT(data->'$.data[1]','$.age') = '20';";
        long result1 = jdbcTemplate.queryForObject(sql1, Long.class);
        Assert.assertEquals(4l, result1);

        // note 要点
//        select * from tab_json where JSON_EXTRACT(data->'$.data','$[0]') = 1;
//        -- 方法2
//        select * from tab_json where JSON_EXTRACT(data,'$.data[1].age') = '20';
//        -- 查到了
//        select * from tab_json where JSON_EXTRACT(data,'$.data[0]') = 1;
//        -- 查不到，类型要对应
//        select * from tab_json where JSON_EXTRACT(data,'$.data[0]') = '1';
    }

}
