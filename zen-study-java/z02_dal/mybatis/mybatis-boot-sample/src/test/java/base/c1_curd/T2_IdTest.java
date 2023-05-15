package base.c1_curd;

import base.BaseBootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.mybatis.boot.sample.base.entity.User;
import zen.hua.mybatis.boot.sample.base.mapper.UserAnnoMapper;
import zen.hua.mybatis.boot.sample.base.mapper.UserMapper;

/**
 * @program: zen-work
 * @description:
 * @author: HUA
 * @create: 2023-03-09 23:26
 **/
public class T2_IdTest extends BaseBootTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAnnoMapper userAnnoMapper;

    /**
     * selectKey 标签作用1：获取自增主键
     */
    @Test
    public void test1SelectKey() {
        User user = new User().setName("li5");
        long li5 = userMapper.addUserForGetId(user);
        System.out.println(li5);
        System.out.println(user.getId());

        // 注解开发
        User user1 = new User().setName("li6");
        int i = userAnnoMapper.addUserForGetId(user1);
        System.out.println(i);
        System.out.println(user1.getId());
    }


    /**
     * selectKey 标签作用2：自定义主键的生成方式
     */
    @Test
    public void test2SelectKey() {
        User user = new User();
        long li5 = userMapper.addUserForGenerateId(user);
        System.out.println(li5);
        System.out.println(user.getName());

        // 注解开发
        int i = userAnnoMapper.addUserForGenerateId(user);
        System.out.println(i);
        System.out.println(user.getName());
    }
}
