package base.c3_result;

import base.BaseBootTest;
import org.junit.Test;
import zen.hua.mybatis.boot.sample.base.entity.UserMapDO;
import zen.hua.mybatis.boot.sample.base.mapper.UserMapDOMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: zen-study-java
 * @description: 返回结果为Map的测试
 * @author: HUA
 * @create: 2023-05-16 21:57
 **/
public class T2_Result_Distinct extends BaseBootTest {

    @Resource
    private UserMapDOMapper userMapDOMapper;

    /**
     * insert into user_map (id, name, sex, type)
     * values (1,"li1",'male',"aaa");
     * insert into user_map (id, name, sex, type)
     * values (1,"li2",'male',"aaa");
     * insert into user_map (id, name, sex, type)
     * values (1,"w1",'female',"aaa");
     * insert into user_map (id, name, sex, type)
     * values (1,"w2",'female',"aaa");
     */
    @Test
    public void testQueryMap() {
        // [{sex=male, type=aaa}, {sex=female, type=aaa}]
        List<Map<String, String>> maps = userMapDOMapper.queryMap();
    }


    /**
     *
     */
    @Test
    public void testQueryDistinct() {
        // mysql 5.7 及其以上版本不支持
//        List<UserMapDO> userMapDOS = userMapDOMapper.queryDistinct();
        // mysql 5.7 及其以上版本配合GROUP BY 关键字一起使用，查询非分组字段可以使用any_value 函数
        List<UserMapDO> userMapDOS = userMapDOMapper.queryDistinct2();
        System.out.println(userMapDOS);
    }



}
