package zen.hua.dal.mp.sample.base.c1_mapper;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.dal.mp.sample.base.BaseTest;
import zen.hua.dal.mp.sample.base.entity.User;
import zen.hua.dal.mp.sample.base.mapper.UserPageMapper;
import zen.hua.dal.mp.sample.base.model.MyPage;
import zen.hua.dal.mp.sample.base.model.ParamSome;
import zen.hua.dal.mp.sample.base.model.UserChildren;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @program: zen-study
 * @description: 分页查询
 * @author: HUA
 * @create: 2023-02-23 22:36
 * 1. 配置分页插件： zen.hua.dal.mp.sample.base.config.MybatisPlusConfig
 **/
@Slf4j
public class T3_PageTest extends BaseTest {

    @Autowired
    private UserPageMapper userPageMapper;

    /**
     * 自带分页插件
     */
    @Test
    public void lambdaPagination() {
        // 当前页，每页条数
        Page<User> page = new Page<>(1, 3);
        Page<User> result = userPageMapper.selectPage(page,
                Wrappers.<User>lambdaQuery()
                        .ge(User::getAge, 1)
                        .orderByAsc(User::getAge));
        assertThat(result.getTotal()).isGreaterThan(3);
        assertThat(result.getRecords().size()).isEqualTo(3);
    }

    /**
     * CESHI
     */
    @Test
    public void tests1() {
        log.error("----------------------------------baseMapper 自带分页-------------------------------------------------------");
        Page<User> page = new Page<>(1, 5);
        page.addOrder(OrderItem.asc("age"));
        Page<User> userIPage = userPageMapper.selectPage(page, Wrappers.<User>lambdaQuery().eq(User::getAge, 20).like(User::getName, "Jack"));
        assertThat(page).isSameAs(userIPage);
        log.error("总条数 -------------> {}", userIPage.getTotal());
        log.error("当前页数 -------------> {}", userIPage.getCurrent());
        log.error("当前每页显示数 -------------> {}", userIPage.getSize());
        List<User> records = userIPage.getRecords();
        assertThat(records).isNotEmpty();

        log.error("----------------------------------json 正反序列化-------------------------------------------------------");
        String json = JSONUtil.toJsonStr(page);
        log.info("json ----------> {}", json);
        Page<User> page1 = JSONUtil.toBean(json, new TypeReference<Page<User>>() {
        },false);
        List<User> records1 = page1.getRecords();
        assertThat(records1).isNotEmpty();
        assertThat(records1.get(0).getClass()).isEqualTo(User.class);

        log.error("----------------------------------自定义 XML 分页-------------------------------------------------------");
        MyPage<User> myPage = new MyPage<User>(1, 5).setSelectInt(20).setSelectStr("Jack");
        ParamSome paramSome = new ParamSome(20, "Jack");
        MyPage<User> userMyPage = userPageMapper.mySelectPage(myPage, paramSome);
        assertThat(myPage).isSameAs(userMyPage);
        log.error("总条数 -------------> {}", userMyPage.getTotal());
        log.error("当前页数 -------------> {}", userMyPage.getCurrent());
        log.error("当前每页显示数 -------------> {}", userMyPage.getSize());
    }

    /**
     * 1. 生成 countSql 会在 left join 的表不参与 where 条件的情况下,把 left join 优化掉
     * 2. 所以建议任何带有 left join 的sql,都写标准sql,即给于表一个别名,字段也要 别名.字段
     */
    @Test
    public void testsLeftJoin() {
        /* 下面的 left join 不会对 count 进行优化,因为 where 条件里有 join 的表的条件 */
        MyPage<UserChildren> myPage = new MyPage<>(1, 5);
        myPage.setSelectInt(18).setSelectStr("Jack");
        // SELECT COUNT(*) AS total
        // FROM user u LEFT JOIN children c ON c.user_id = u.id
        // WHERE u.age = ? AND c.name = ?
        MyPage<UserChildren> userChildrenMyPage = userPageMapper.userChildrenPage(myPage);
        List<UserChildren> records = userChildrenMyPage.getRecords();
        records.forEach(System.out::println);

        /* 下面的 left join 会对 count 进行优化,因为 where 条件里没有 join 的表的条件 */
        myPage = new MyPage<UserChildren>(1, 5).setSelectInt(18);
        // SELECT COUNT(*) AS total FROM user u WHERE u.age = ?
        userChildrenMyPage = userPageMapper.userChildrenPage(myPage);
        records = userChildrenMyPage.getRecords();
        records.forEach(System.out::println);
    }


    /**
     * 如果想临时不分页,可以在初始化IPage时size参数传 <0 的值;
     * select * from user WHERE name like '%'
     */
    @Test
    public void testMyPageMap() {
        MyPage<User> myPage = new MyPage<User>(1, -1);
        userPageMapper.mySelectPageMap(myPage, Maps.newHashMap("name", "%"));
        myPage.getRecords().forEach(System.out::println);
    }

    /**
     * 如果返回类型是 List 则入参的 IPage 可以为 null(为 null 则不分页),但需要你手动 入参的IPage.setRecords(返回的 List);
     * 默认执行
     * select * from user
     * setRecords执行，没有区别？？
     * select * from user
     */
    @Test
    public void myPage() {
        MyPage<User> page = new MyPage<>(1, 5);
        page.setName("a");
        List<User> users = userPageMapper.myPageSelect(null);
        page.setRecords(users);
        log.error("总条数 -------------> {}", page.getTotal());
        log.error("当前页数 -------------> {}", page.getCurrent());
        log.error("当前每页显示数 -------------> {}", page.getSize());
    }

    /**
     * 只查询当前页的记录，不查询总记录数
     */
    @Test
    public void currentPageListTest() {
        // 使用三参数的构造器创建Page对象
        // 第三个参数isSearchCount：传true则查询总记录数;传false则不查询总记录数（既不进行count查询）
        Page<User> page = new Page<>(1,3,false);
        Page<User> result = userPageMapper.selectPage(page, Wrappers.<User>lambdaQuery().ge(User::getAge, 20));
        assertThat(result.getRecords().size()).isEqualTo(3);
        //因为没有进行count查询，total值为0
        assertThat(result.getTotal()).isEqualTo(0);
    }
}
