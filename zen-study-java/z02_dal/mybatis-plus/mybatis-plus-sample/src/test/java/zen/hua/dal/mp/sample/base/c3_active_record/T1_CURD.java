package zen.hua.dal.mp.sample.base.c3_active_record;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import zen.hua.dal.mp.sample.base.BaseTest;
import zen.hua.dal.mp.sample.base.entity.UserActiveRecord;

import java.util.List;

/**
 * @program: zen-study
 * @description:
 * @author: HUA
 * @create: 2023-02-25 10:22
 **/
public class T1_CURD extends BaseTest {

    /**
     * 测试查询
     */
    @Test
    public void testARSelectById(){

        UserActiveRecord user = new UserActiveRecord();
        user.setId(1L);
        // SELECT id,name,age,email FROM user WHERE id=?
        UserActiveRecord user1 = user.selectById();
        System.out.println(user1);
    }


    /**
     * 新增数据
     */
    @Test
    public void testARInsert(){
        // INSERT INTO user ( name, age, email ) VALUES ( ?, ?, ? )
        UserActiveRecord user = new UserActiveRecord();
        user.setName("hua");
        user.setAge(18);
        user.setEmail("hua@hua.com");

        boolean insert = user.insert();
        System.out.println(insert);
    }

    /**
     * 更新操作
     */
    @Test
    public void testUpdateById(){
        UserActiveRecord user = new UserActiveRecord();
        user.setId(12L);
        user.setName("hua");
        // UPDATE user SET name=? WHERE id=?
        boolean insert = user.updateById();
        System.out.println(insert);
    }

    /**
     * 删除操作
     */
    @Test
    public void testDeleteById(){

        UserActiveRecord user = new UserActiveRecord();
        //user.setId(16L); // 方式1

        boolean b = user.deleteById(12L);
        System.out.println(b);
    }


    /**
     * 根据条件进行查找
     */
    @Test
    public void testARFindByWrapper(){
        // SELECT id,name,age,email FROM user WHERE (age >= ?)
        QueryWrapper<UserActiveRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age","20");

        UserActiveRecord user = new UserActiveRecord();
        List<UserActiveRecord> users = user.selectList(queryWrapper);
        for (UserActiveRecord user1 : users) {
            System.out.println(user1);
        }

    }

}
