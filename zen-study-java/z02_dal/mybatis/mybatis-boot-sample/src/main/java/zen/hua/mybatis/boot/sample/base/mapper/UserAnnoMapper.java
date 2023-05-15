package zen.hua.mybatis.boot.sample.base.mapper;

import org.apache.ibatis.annotations.*;
import zen.hua.mybatis.boot.sample.base.entity.User;

import java.util.List;

// 推荐使用@MapperSan
@Mapper
public interface UserAnnoMapper {

    /**
     * 查询全部用户
     * @return
     */
    @Select("select * from user")
    List<User> findAll();

    /**
     * 根据id查询单个用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);

    /**
     * cud方法可以返回boolean
     * @param user
     * @return 变更条数>0为false
     */
    @Insert("insert into user (name) values(#{name})")
    boolean insertOne(User user);

    /**
     * 根据id进行更新
     * @param user
     * @return 操作条数
     */
    @Update("update user set name=#{name} where id=#{id}")
    int updateById(User user);

    /**
     * 根据id进行删除
     *
     * @param id
     * @return
     */
    @Delete("delete from user where id=#{id}")
    int deleteById(Long id);

    // 注解不适合复杂的组装sql
    @Select("<script>" +
            "select * from user\n" +
            "        where 1=1\n" +
            "        <if test=\"id != null\">\n" +
            "            and id = #{id}\n" +
            "        </if>\n" +
            "        <if test=\"name != null and name != ''\">\n" +
            "            <!--模糊匹配函数, 不能识别'  ' -->\n" +
            "           and name like concat('%',#{name},'%')\n" +
            "        </if>" +
            "</script>")
    List<User> findByIf(User user);

    /**
     * @SelectKey的属性有下面几个：
     *
     * 1. statement属性：填入将会被执行的 SQL 字符串数组。
     * 2. keyProperty属性：填入将会被更新的参数对象的属性的值。
     * 3. before属性：填入 true 或 false 以指明 SQL 语句应被在插入语句的之前还是之后执行。
     * 4. resultType属性：填入 keyProperty 的 Java 类型。
     * 5. statementType属性：填入Statement、 PreparedStatement 和 CallableStatement
     *  中的 STATEMENT、PREPARED 或 CALLABLE 中任一值填入 。默认值是 PREPARED。
     */

    /**
     * selectKey 标签作用1：获取自增主键
     *  自增id塞进user主键字段
     * @return 新增条数
     */
    @Insert("INSERT INTO USER(NAME) VALUES(#{name})")
    @SelectKey(statement="select LAST_INSERT_ID()",
            keyProperty="id",
            resultType=Long.class, before=false)
    int addUserForGetId(User user);

    /**
     * selectKey 标签作用2：自定义主键的生成方式
     * @param user
     * @return
     */
    @Insert("INSERT INTO USER(NAME) VALUES(#{name})")
    @SelectKey(statement="SELECT UUID()",
            keyProperty="name",  // 应该是id, 方便模拟测试
            resultType=String.class, before=true)
    int addUserForGenerateId(User user);

}
