package base.c2_dynamic;

import base.BaseBootTest;
import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.mybatis.boot.sample.base.mapper.UserMapper;

import java.util.Arrays;
import java.util.Map;

/**
 * @program: zen-study
 * @description: 测试foreach标签
 * @author: HUA
 * @create: 2023-03-11 12:18
 **/
@Slf4j
public class T2_Foreach extends BaseBootTest {

    @Autowired
    private UserMapper userMapper;


    /**
     * 遍历list
     */
    @Test
    public void test1List() {
        log.error("====================== 遍历list，collection属性值默认为list。 ========================");
        // select * from user where id in ( 1 , 2 , 3 )
        userMapper.findByForEachList(Arrays.asList(1l,2l,3l));
        log.error("====================== 遍历list，通过@Param指定collection属性值。 ========================");
        // select * from user where id in ( 1 , 2 , 3 )
        userMapper.findByForEachList2(Arrays.asList(1l,2l,3l));
    }

    /**
     * 遍历array
     */
    @Test
    public void test1Array() {
        log.error("====================== 遍历array，collection属性值默认为array。 ========================");
        // select * from user where id in ( 1 , 2 , 3 )
        userMapper.findByForEachArray(new Long[]{1l,2l,3l});
        log.error("====================== 遍历array，通过@Param指定collection属性值。 ========================");
        // select * from user where id in ( 1 , 2 , 3 )
        userMapper.findByForEachArray2(new Long[]{1l,2l,3l});
    }

    /**
     * 遍历map
     */
    @Test
    public void test3Map() {
        Map<String,Long>  map = MapUtil.ofEntries(
                MapUtil.entry("li1",1l),
                MapUtil.entry("li2",2l),
                MapUtil.entry("li3",3l)
        );
        // select * from user where (name,id) IN ( ('li2',2) , ('li1',1) , ('li3',3) )
        log.error("====================== 遍历map，使用键值对。 ========================");
        // select * from user where id in ( 1 , 2 , 3 )
        userMapper.findByForEachMap1(map);
        log.error("====================== 遍历map，使用值集合。 ========================");
        // select * from user where id in ( 1 , 2 , 3 )
        userMapper.findByForEachMap2(map);
        log.error("====================== 遍历map，使用键集合。 ========================");
        // select * from user where name in ( 'li2' , 'li1' , 'li3' )
        userMapper.findByForEachMap3(map);
    }
}
