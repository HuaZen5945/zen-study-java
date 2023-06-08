package base.c5_batch;

import base.BaseBootTest;
import cn.hutool.core.collection.CollUtil;
import org.junit.Test;
import zen.hua.mybatis.boot.sample.base.entity.User;
import zen.hua.mybatis.boot.sample.base.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: zen-study-java
 * @description: 批量操作
 * @author: HUA
 * @create: 2023-06-08 21:45
 **/
public class T1_BatchOperate extends BaseBootTest {

    @Resource
    private UserMapper userMapper;

    /**
     * 批量更新
     * 1. 数据连接增加配置 allowMultiQueries=true, 支持”;"号分隔的多条语句的执行。
     * <url>
     *     spring.datasource.url=jdbc:mysql://localhost:3306/test?allowMultiQueries=true
     * </url>
     */
    @Test
    public void testBatchUpdate() {
        List<User> userList = CollUtil.newArrayList(
                new User().setId(1l).setName("hua1"),
                new User().setId(2l).setName("hua2"),
                new User().setId(3l).setName("hua3")
        );
        /*
            执行的sql：
            update `user` set `name` = ? where id = ? ;
            update `user` set `name` = ? where id = ? ;
            update `user` set `name` = ? where id = ?
         */
        boolean b = userMapper.batchUpdate(userList);
        //  [User(id=1, name=hua1), User(id=2, name=hua2), User(id=3, name=hua3), User(id=4, name=li4)]
        List<User> mapperAll = userMapper.findAll();
        System.out.println(mapperAll);
    }
}
