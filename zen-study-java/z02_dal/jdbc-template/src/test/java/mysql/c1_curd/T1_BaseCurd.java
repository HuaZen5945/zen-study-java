package mysql.c1_curd;

import mysql.MysqlBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import zen.hua.jdbc.template.mysql.entity.User;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @program: zen-study-java
 * @description: 基本增删改查
 * @author: HUA
 * @create: 2023-05-17 21:40
 **/
public class T1_BaseCurd extends MysqlBaseTest {


    //Junit单元测试，可以让方法独立执行
    //1. 获取JDBCTemplate对象，填入数据源，spring默认填入
//    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

//    2 spring依赖管理方式
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 1. 添加一条记录
     */
    @Test
    public void testInsert() {
        //2. 定义sql
        String sql = "insert into user (name) values (?)";
        //3. 执行sql
        int count = jdbcTemplate.update(sql,"郭靖");
        Assert.assertEquals(1,count);
    }

    /**
     * 2. 修改一条记录
     */
    @Test
    public void testUpdate() {
        String sql = "update user set name = ? where id = ?";
        int count = jdbcTemplate.update(sql, "li11",1);
        Assert.assertEquals(1,count);
    }

    /**
     * 3.删除刚才添加的记录
     */
    @Test
    public void testDelete() {
        String sql = "delete from user where id = ?";
        int count = jdbcTemplate.update(sql, 1);
        System.out.println(count);
    }

    /**
     * 4.查询id为1的记录，将其封装为Map集合
     * 注意：这个方法查询的结果集长度只能是1
     */
    @Test
    public void testQueryOne() {
        String sql = "select * from user where id = ?";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, 1);
        System.out.println(map);
        // {id=1, name=li1}
    }

    /**
     * 5. 查询所有记录，将其封装为List
     */
    @Test
    public void testQueryList1() {
        String sql = "select * from user";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap);
        }

//        {id=1, name=li1}
//        {id=2, name=li2}
    }

    /**
     * 6. 查询所有记录，将其封装为Emp对象的List集合
     */
    @Test
    public void testQueryList2() {
        String sql = "select * from user";
        List<User> list = jdbcTemplate.query(sql, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                long id = rs.getLong("id");
                String name = rs.getString("name");
                user.setId(id);
                user.setName(name);
                return user;
            }
        });


        for (User emp : list) {
            System.out.println(emp);
        }
//        User(id=1, name=li1)
//        User(id=2, name=li2)
    }

    /**
     * 6. 查询所有记录，将其封装为Emp对象的List集合
     */
    @Test
    public void testQueryList3() {
        String sql = "select * from user";
        List<User> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        for (User emp : list) {
            System.out.println(emp);
        }
        // User(id=1, name=li1)
        // User(id=2, name=li2)
    }

    /**
     * 7. 查询总记录数
     */
    @Test
    public void test7() {
        String sql = "select count(id) from user";
        // The query is expected to be a single row/single column query
        Long total = jdbcTemplate.queryForObject(sql, Long.class);
        System.out.println(total);
    }


}
