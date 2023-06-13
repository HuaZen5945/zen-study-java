package zen.hua.dal.mp.sample.base.c1_mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.dal.mp.sample.base.BaseTest;
import zen.hua.dal.mp.sample.base.entity.User;
import zen.hua.dal.mp.sample.base.mapper.UserMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zen-study
 * @description: 测试条件构建器
 * @author: HUA
 * @create: 2023-02-23 23:52
 * 1. 第一个入参boolean condition表示该条件是否加入最后生成的sql中
 * 2. R column均表示数据库字段,当R具体类型为String时则为数据库字段名 (字段名是数据库关键字的自己用转义符包裹!)!
 * 3.
 **/
public class T4_WrapperTest extends BaseTest {


    @Autowired
    private UserMapper userMapper;

    /**
     * allEq(Map<R, V> params)
     * allEq(Map<R, V> params, boolean null2IsNull)
     * allEq(boolean condition, Map<R, V> params, boolean null2IsNull)
     * <p>
     * condition：表示该条件是否加入最后生成的sql中
     * params : key为数据库字段名,value为字段值
     * null2IsNull : 为true则在map的value为null时调用 isNull 方法,为false时则忽略value为null的
     */
    @Test
    public void testAllEq1() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 构建map
        Map<String, Object> map = new HashMap<>();
        map.put("name", "jack");
        map.put("age", null);

        //  WHERE name = ? AND age IS NULL
        // queryWrapper.allEq(map);

        // WHERE name = ?
        //  queryWrapper.allEq(map,false);

        //  第一个参数为false时，不加入最终条件
        // queryWrapper.allEq(false,map,true);


        userMapper.selectList(queryWrapper);
    }

    /**
     * allEq(BiPredicate<R, V> filter, Map<R, V> params)
     * allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
     * allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
     * <p>
     * filter : 过滤函数,是否允许字段传入比对条件中
     * params 与 null2IsNull : 同上
     */
    @Test
    public void testAllEq2() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 构建map
        Map<String, Object> map = new HashMap<>();
        map.put("name", "jack");
        map.put("age", null);

        // WHERE age IS NULL：允许map中字段名不是name的字段加入比较条件
        // k: map-key, v:map-value
        queryWrapper.allEq((k, v) -> !k.equals("name"), map);
        userMapper.selectList(queryWrapper);
    }


    /**
     * 基本比较操作
     * ● eq 等于 =
     * ● ne 不等于 <>
     * ● gt 大于 >
     * ● ge 大于等于 >=
     * ● lt 小于 <
     * ● le 小于等于 <=
     * ● between  BETWEEN 值1 AND 值2
     * ● notBetween NOT BETWEEN 值1 AND 值2
     * ● in 字段 IN (value.get(0), value.get(1), ...)
     * ● notIn 字段 NOT IN (v0, v1, ...)
     */
    @Test
    public void testWrapperBase() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        boolean flag = true;

        queryWrapper
                // email = ? AND age >= ?
                .eq("email", "hua@hua.com")
                .ge("age", 20)

                // in
                // name IN (?,?)
                .in("name", "Jack", "hua")
                // name NOT IN (?,?)
                .notIn("name", Arrays.asList("Jack", "hua"))
                // age IN (1,2,3,4,5,6)
                .inSql("age", "1,2,3,4,5,6")
                // id IN (select id from user where id <=3)
                .inSql("id", "select id from user where id <=3")


                // null
                // email IS NULL
                .isNull("email")
                // id IS NOT NULL
                .isNotNull("id")
                // 针对不同的情况采取不同的执行情况
                .func(i -> {
                    if (flag) i.eq("id", 1);
                    else i.ne("id", 1);
                })
        ;

        userMapper.selectList(queryWrapper);
    }

    /**
     * ● like LIKE '%值%'
     * 例: like("name", "王") ---> name like '%王%'
     * ● notLike NOT LIKE '%值%'
     * 例: notLike("name", "王") ---> name not like '%王%'
     * ● likeLeft LIKE '%值'
     * 例: likeLeft("name", "王") ---> name like '%王'
     * ● likeRight LIKE '值%'
     * 例: likeRight("name", "王") ---> name like '王%'
     */
    @Test
    public void testWrapperLike() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // WHERE name LIKE %华%
        queryWrapper.like("name", "华");
        userMapper.selectList(queryWrapper);
    }

    /**
     * ● orderBy 排序：ORDER BY 字段, ...
     * 例: orderBy(true, true, "id", "name") ---> order by id ASC,name ASC
     * ● orderByAsc 排序：ORDER BY 字段, ... ASC
     * 例 : orderByAsc("id", "name") ---> order by id ASC,name ASC
     * ● orderByDesc 排序：ORDER BY 字段, ... DESC
     * 例 : orderByDesc("id", "name") ---> order by id DESC,name DESC
     */
    @Test
    public void testWrapperOrder() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // ORDER BY age DESC
        queryWrapper.orderByDesc("age");

        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试分组相关sql
     */
    @Test
    public void testGroup() {
        QueryWrapper<User> queryWrapper = Wrappers.<User>query()
                // GROUP BY name,age
                .groupBy("name", "age")
                // HAVING sum(age)>10
//                .having("sum(age)>10")
                // having只有一个生效
                // HAVING sum(age)>? and sum(age)<?
                .having("sum(age)>{0} and sum(age)<{1}", 11, 20)
                // 会按sql正确书写顺序进行拼接
                .gt("id", 0);
        userMapper.selectList(queryWrapper);
    }

    @Test
    public void testAndOR1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // WHERE name = ? OR email = ?
        queryWrapper.eq("name", "hua")
                .or()
                .eq("email", "hua@lagou.com");
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * and or 嵌套
     */
    @Test
    public void testAndOR2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // WHERE name = ? AND ( age = ? ) OR ( email = ? )
        queryWrapper.eq("name", "庆华")
                .and(i -> i.eq("age", 26))
                .or(i -> i.eq("email", "hua@lagou.com"))
                // 正常嵌套 不带 AND 或者 OR
//                .nested(i -> i.eq("name", "李白").ne("status", "活着"))
        ;

        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 组装sql
     */
    @Test
    public void testBuildSql() {
        QueryWrapper<User> queryWrapper = Wrappers.<User>query()
                // (id = 2)
                .apply("id = 2")
//                .apply("date_format(dateColumn,'%Y-%m-%d') = {0}", "2008-08-08")
                // 无视优化规则直接拼接到 sql 的最后
                // 只能调用一次,多次调用以最后一次为准 有sql注入的风险,请谨慎使用
//                .last("limit 1")
                // limit 1
                .last(String.format("limit %d", 1));
        userMapper.selectList(queryWrapper);
    }


    /**
     * 测试exist
     */
    @Test
    public void testExist() {
        QueryWrapper<User> queryWrapper = Wrappers.<User>query()
                // WHERE (EXISTS (select id from user where id > 0)
                .exists("select id from user where id > 0")
                // AND NOT EXISTS (select id from user where id > 10))
                .notExists("select id from user where id > 10");
        userMapper.selectList(queryWrapper);
    }

    @Test
    public void testSelectColumn() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // SELECT name,age FROM user WHERE age >= ?
        queryWrapper.ge("age", 20)
                .select("name", "age")
                // 过滤查询字段(主键除外),入参不包含 class 的调用前需要wrapper内的entity属性有值!
                .select(User.class,i -> i.getProperty().startsWith("a"))
                ;
        userMapper.selectList(queryWrapper);

    }

    @Test
    public void testSet() {
        // UPDATE user SET name='hua',email='',age=null
        UpdateWrapper<User> updateWrapper = Wrappers.<User>update()
                .set("name","hua")
                // 设置为空串
                .set("email","")
                // 设置为null
                .set("age",null)
                // 设置部分sql
//                .setSql("name = '华'")
                ;
        userMapper.update(null,updateWrapper);
    }

    @Test
    public void testLambda() {
        // 等价示例：
        QueryWrapper<User> queryWrapper = Wrappers.<User>query();
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.<User>lambdaQuery();
        queryWrapper.eq("id", 1);
        lambdaQuery.eq(User::getId, 1);

        // 等价示例：
        UpdateWrapper<User> updateWrapper = Wrappers.<User>update();
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = Wrappers.<User>lambdaUpdate();
        updateWrapper.eq("id", 1);
        lambdaUpdateWrapper.eq(User::getId, 1);

    }
}
