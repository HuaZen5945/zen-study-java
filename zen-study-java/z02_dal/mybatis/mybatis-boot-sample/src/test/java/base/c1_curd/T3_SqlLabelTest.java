package base.c1_curd;

import base.BaseBootTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.mybatis.boot.sample.base.entity.User;
import zen.hua.mybatis.boot.sample.base.mapper.UserMapper;

/**
 * @program: zen-study
 * @description: sql标签的使用
 * @author: HUA
 * @create: 2023-03-11 09:07
 **/
public class T3_SqlLabelTest extends BaseBootTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 测试sql标签基本用法
     */
    @Test
    public void test1Sql() {
        User user  = userMapper.findForBaseSql(2l);
        Assert.assertNotNull(user);
        Assert.assertEquals("li2",user.getName());
    }

    /**
     * 测试sql标签的静态参数化
     */
    @Test
    public void test2SqlParam() {
        User user  = userMapper.findForParamSql(2l);
        Assert.assertNotNull(user);
        Assert.assertEquals("li2",user.getName());
    }

}
