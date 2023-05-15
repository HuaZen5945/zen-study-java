package zen.hua.dal.mp.sample.base.c1_mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.dal.mp.sample.base.BaseTest;
import zen.hua.dal.mp.sample.base.entity.User;
import zen.hua.dal.mp.sample.base.mapper.UserMapper;

import java.util.Arrays;
import java.util.List;

/**
 * @program: zen-work
 * @description: 查询测试
 * @author: HUA
 * @create: 2023-02-23 22:25
 **/
public class T2_SelectTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * SELECT id,name,age,email FROM user WHERE id=?
     * 根据ID进行查询
     * 1. 需要有 @TableId 指明主键
     */
    @Test
    public void testSelectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    /**
     * 根据ID进行批量查询
     * SELECT id,name,age,email FROM user WHERE id IN ( ? , ? )
     */
    @Test
    public void testSelectBatchIds() {

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 3l));
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试selectOne
     * SELECT id,name,age,email FROM user WHERE (name = ?)
     */
    @Test
    public void testSelectOne() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "华");

        // 根据条件查询一条记录，如果查询结果超过一条，会报错
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    /**
     * 根据wrapper条件，查询总记录数
     * SELECT COUNT( * ) FROM user WHERE (age > ?)
     */
    @Test
    public void testSelectCount() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age", 18); // 查询年龄大于18的

        Long count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    /**
     * SELECT id,name,age,email FROM user WHERE (age > ?)
     * 根据wrapper条件，查询列表
     * 1. sql一样，只是用list接收
     */
    @Test
    public void testSelectList() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age", 18); // 查询年龄大于18的

        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }

    }
}
