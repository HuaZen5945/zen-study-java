package base.c1_curd;

import base.BaseBootTest;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.mybatis.boot.sample.base.entity.User;
import zen.hua.mybatis.boot.sample.base.mapper.UserAnnoMapper;
import zen.hua.mybatis.boot.sample.base.mapper.UserMapper;

import java.util.List;

@Slf4j
@FixMethodOrder(MethodSorters.JVM)
public class T1_BaseCurdTest extends BaseBootTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAnnoMapper userAnnoMapper;

    /**
     * 测试查询所有
     */
    @Test
    public void test1FindAll() {
        List<User> userList = userMapper.findAll();
        Assert.assertNotNull(userList);
        List<User> userList1 = userAnnoMapper.findAll();
        Assert.assertNotNull(userList1);
    }

    /**
     * 测试根据主键查询单个
     */
    @Test
    public void test2FindById() {
        User user = userMapper.findById(1L);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(),"li1");

        User user1 = userAnnoMapper.findById(1L);
        Assert.assertNotNull(user1);
        Assert.assertEquals(user1.getName(),"li1");
    }

    /**
     * 保存
     */
    @Test
    public void test3InsertOne() {
        User user = new User().setName("li5");
        boolean insertOne = userMapper.insertOne(user);
        Assert.assertEquals(insertOne,true);

        User user1 = new User().setName("li55");
        boolean insertOne1 = userAnnoMapper.insertOne(user1);
        Assert.assertEquals(insertOne1,true);
    }

    /**
     * 根据主键进行变更
     */
    @Test
    public void test4UpdateById() {
        User user = new User().setId(1L).setName("李一");
        int update = userMapper.updateById(user);
        Assert.assertEquals(1,update);

        User user1 = new User().setId(1L).setName("李一");
        int update1 = userAnnoMapper.updateById(user1);
        Assert.assertEquals(1,update1);
    }

    /**
     * 测试根据主键删除
     */
    @Test
    public void test5DeleteById() {
        int i = userMapper.deleteById(1L);
        Assert.assertEquals(1, i);

        int i1 = userAnnoMapper.deleteById(1L);
        Assert.assertEquals(0, i1);
    }
}
