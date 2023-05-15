package zen.hua.dal.mp.sample.base.c1_mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.dal.mp.sample.base.BaseTest;
import zen.hua.dal.mp.sample.base.entity.User;
import zen.hua.dal.mp.sample.base.mapper.UserMapper;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @program: zen-work
 * @description: mapper 接口的cud操作
 * @author: HUA
 * @create: 2023-02-23 21:47
 * 1. 建议开启mp日志
 **/
public class T1_CudTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;


    /**
     * 测试添加
     * 1 默认执行
     * INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
     * 2 @TableId(type = IdType.AUTO)
     * INSERT INTO user ( name, age, email ) VALUES ( ?, ?, ? )
     */
    @Test
    public void testInsert() {
        User user = new User();
        user.setName("hua");
        user.setAge(20);
        user.setEmail("hua@lagou.com");

        // 返回值是影响的行数
        int result = userMapper.insert(user);
        System.out.println(result);

        System.out.println("id值为" + user.getId());
    }

    /**
     * 根据id进行变更
     * 1. 需要有 @TableId 指明主键
     * 2. null值不参与更新
     * UPDATE user SET age=? WHERE id=?
     */
    @Test
    public void testUpdateById() {
        User user = new User();
        user.setId(6L);
        user.setAge(30);

        int i = userMapper.updateById(user);
        System.out.println(i);
    }


    /**
     * 测试根据条件进行修改
     * UPDATE user SET age=? WHERE (name = ?)
     * 1. Wrapper默认不支持驼峰
     */
    @Test
    public void testUpdate() {

        // 1. 更新的字段
        User user = new User();
        user.setAge(35);

        // 2.更新的条件
        // com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "hua");

        // 对于字段为空部分，不进行修改，即此处只修改年龄
        int i = userMapper.update(user, queryWrapper);
        System.out.println(i);
    }

    /**
     * UPDATE user SET age=? WHERE (id = ?)
     */
    @Test
    public void testUpdate2() {
        // com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("id", 6)
                .set("age", 40);

        int i = userMapper.update(null, updateWrapper);
        System.out.println(i);
    }


    /**
     * 根据ID进行删除
     * DELETE FROM user WHERE id=?
     */
    @Test
    public void testDeleteById() {
        int i = userMapper.deleteById(2L);
        System.out.println(i);
    }

    /**
     * DELETE FROM user WHERE name = ? AND age = ?
     * 根据columnMap进行删除
     * 1.  默认不支持key用驼峰
     */
    @Test
    public void testDeleteByMap() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "hua");
        map.put("age", 35);

        // 将columnMap中的元素设置为删除的条件，多个条件是and的关系
        int i = userMapper.deleteByMap(map);
        System.out.println(i);
    }


    /**
     * DELETE FROM user WHERE name=? AND age=?
     */
    @Test
    public void testDelete1(){

        User user = new User();
        user.setName("hua");
        user.setAge(26);

        QueryWrapper<User> ueryWrapper = new QueryWrapper<>(user);

        int i = userMapper.delete(ueryWrapper);
        System.out.println(i);
    }

    /**
     * DELETE FROM user WHERE (name = ? AND age = ?)
     */
    @Test
    public void testDelete2(){
        QueryWrapper<User> ueryWrapper = new QueryWrapper<>();
        ueryWrapper.eq("name","hua").eq("age",26);

        int i = userMapper.delete(ueryWrapper);
        System.out.println(i);
    }


    /**
     * DELETE FROM user WHERE name=? AND age=? AND (name = ? AND age = ?)
     */
    @Test
    public void testDelete3(){

        User user = new User();
        user.setName("华");
        user.setAge(26);

        QueryWrapper<User> ueryWrapper = new QueryWrapper<>(user);
        ueryWrapper.eq("name","hua").eq("age",26);

        int i = userMapper.delete(ueryWrapper);
        System.out.println(i);
    }

    /**
     * DELETE FROM user WHERE id IN ( ? , ? )
     */
    @Test
    public void testDeleteBatchIds(){
        // 根据id集合批量删除
        int i = userMapper.deleteBatchIds(Arrays.asList(10l,11l));
        System.out.println(i);
    }
}
