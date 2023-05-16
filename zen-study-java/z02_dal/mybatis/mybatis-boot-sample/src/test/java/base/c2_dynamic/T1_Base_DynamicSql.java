package base.c2_dynamic;

import base.BaseBootTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.mybatis.boot.sample.base.entity.User;
import zen.hua.mybatis.boot.sample.base.mapper.UserAnnoMapper;
import zen.hua.mybatis.boot.sample.base.mapper.UserMapper;

/**
 * @program: zen-work
 * @description: 动态sql测试
 * @author: HUA
 * @create: 2023-03-09 21:02
 **/
@Slf4j
public class T1_Base_DynamicSql extends BaseBootTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAnnoMapper userAnnoMapper;


    /**
     * 测试if标签
     */
    @Test
    public void test1If() {
        log.error("====================== name为null ========================");
        // select * from user where 1=1 and id = 1
        userMapper.findByIf(new User().setId(1l).setName(null));
        log.error("====================== name为'' ========================");
        // select * from user where 1=1 and id = 1
        userMapper.findByIf(new User().setId(1l).setName(""));
        log.error("====================== name为'    ' ========================");
        // 不能识别'  ' ，name != ''
        // select * from user where 1=1 and id = 1 and name like concat('%','   ','%')
        // 能识别， name != null and name.trim().length() > 0
        // select * from user where 1=1 and id = ?
        userMapper.findByIf(new User().setId(1l).setName("    "));
        log.error("====================== name为li ========================");
        // select * from user where 1=1 and id = 1  and name like concat('%','li','%')
        userMapper.findByIf(new User().setId(1l).setName("li"));
        log.error("====================== name为li, 采用注解方式 ========================");
        // select * from user where 1=1 and id = 1  and name like concat('%','li','%')
        userAnnoMapper.findByIf(new User().setId(1l).setName("li"));
    }

    /**
     * 测试if …… else
     */
    @Test
    public void test2IfElse() {
        log.error("====================== name为li2 ========================");
        // select * from user where 1=1 and id = 2
        userMapper.findByIfElse(new User().setName("li2"));
        log.error("====================== id为1，满足另一个when ========================");
        // select * from user where 1=1 and id = 3
        userMapper.findByIfElse(new User().setId(1l));
        log.error("====================== 两个when都满足，只执行第一个。 ========================");
        // select * from user where 1=1 and id = 2
        userMapper.findByIfElse(new User().setId(1l).setName("li2"));
        log.error("====================== name为其他情况 ========================");
        // select * from user where 1=1 and id > 3
        userMapper.findByIfElse(new User().setName(""));
    }

    /**
     * 测试where标签
     */
    @Test
    public void test3Where() {
        log.error("====================== id为null，name为null，没有匹配where语句，不会有where子句 ========================");
        // select * from user
        userMapper.findByWhere(new User());
        log.error("====================== iid为null，name不为''，会移除and 前缀 ========================");
        // select * from user WHERE name like concat('%',?,'%')
        userMapper.findByWhere(new User().setName("li"));
    }

    /**
     * 测试set标签
     */
    @Test
    public void test4Set() {
        log.error("====================== name为null，没有待执行的set语句 ========================");
        // update user  where id = ?  报错
//        userMapper.updateBySet(new User().setId(1l));
        log.error("====================== name不为''，会移除逗号后缀 ========================");
        // update user SET name = ? where id = ?
        userMapper.updateBySet(new User().setName("li11").setId(1l));
    }

    /**
     * 测试bind标签，用于生成新变量
     */
    @Test
    public void test5Bind() {
        // SELECT * FROM user WHERE name LIKE 'li%'
        userMapper.findByBind(new User().setName("li"));
    }

    /**
     * trim标签，生成类似where标签的效果
     */
    @Test
    public void test6TrimWhere() {
        log.error("====================== id为null，name为null，没有匹配where语句，不会有where子句 ========================");
        // select * from user
        userMapper.findByTrimWhere(new User());
        log.error("====================== iid为null，name不为''，会移除and 前缀 ========================");
        // select * from user WHERE name = li
        userMapper.findByTrimWhere(new User().setName("li"));
    }

    /**
     * trim标签，生成类似set标签的效果
     */
    @Test
    public void test6TrimSet() {
        log.error("====================== name为null，没有待执行的set语句 ========================");
        // update user   报错
//        userMapper.updateByTrimSet(new User().setId(1l));
        log.error("====================== name不为''，会移除逗号后缀 ========================");
        // update user SET name = ? where id = ?
        userMapper.updateByTrimSet(new User().setName("li11").setId(1l));
    }
}
