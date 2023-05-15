package zen.hua.mybatis.boot.sample.base.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zen.hua.mybatis.boot.sample.base.entity.User;

import java.util.List;
import java.util.Map;

// 推荐使用@MapperSan
@Mapper
public interface UserMapper {

    /**
     * 查询全部用户
     * @return
     */
    List<User> findAll();

    /**
     * 根据id查询单个用户
     * @param id
     * @return
     */
    User findById(@Param("id") Long id);

    /**
     * cud方法可以返回boolean
     * @param user
     * @return 变更条数>0为false
     */
    boolean insertOne(User user);

    /**
     * 根据id进行更新
     * @param user
     * @return 操作条数
     */
    int updateById(User user);

    /**
     * 根据id进行删除
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    //=======================================  dynamic_sql ====================


    /**
     * 使用if标签
     *
     * @param user
     * @return
     */
    List<User> findByIf(User user);

    /**
     *  类似 if ELSEIF ELSE，WHEN语句只会执行一条。
     */
    List<User> findByIfElse(User user);

    /**
     * 使用where标签
     * 1. 只有在一个以上的if条件有值的情况下才去插入WHERE子句。
     * 2. 若最后的内容是“AND”或“OR”开头，则where元素也知道如何将它们去除。
     * @param user
     * @return
     */
    List<User> findByWhere(User user);

    /**
     * 使用set标签
     * 1. 移除无用的逗号后缀
     * @param user
     * @return
     */
    int updateBySet(User user);


    /**
     * bind 标签可以用来在映射文件中定义变量，然后将输入参数中的值拼接其他字符串后组成新的字符串赋值给该变量
     * @param li
     * @return
     */
    List<User> findByBind(User li);

    /**
     * trim组装sql
     * 1. prefix 是给整个字符串增加一个前缀。
     * 2. prefixOverrides 则是去掉整个字符串前面多余的字符。
     * 3. suffix 是给整个字符串增加一个后缀.
     * 4. suffixOverrides 则是去掉整个字符串后面多余的字符。
     * @param user
     * @return
     */
    List<User> findByTrimWhere(User user);

    /**
     * trim 实现类似set标签效果
     * @param user
     * @return
     */
    int updateByTrimSet(User user);

    // ============================== dynamic sql 之 foreach ================

    /**
     * 参数类型为List时，collection的默认属性值为list
     *
     * @param idList
     * @return
     */
    List<User> findByForEachList(List<Long> idList);

    /**
     * 参数类型为List时，@Param注解 指定collection的属性值
     *
     * @param idList
     * @return
     */
    List<User> findByForEachList2(@Param("idList") List<Long> idList);

    /**
     * 参数类型为Array时，collection的默认属性值为array
     *
     * @param idArray
     * @return
     */
    List<User> findByForEachArray(Long[] idArray);

    /**
     * 参数类型为Array时，@Param注解 指定collection的属性值
     *
     * @param idArray
     * @return
     */
    List<User> findByForEachArray2(@Param("idArray") Long[] idArray);

    /**
     * 参数类型为Map时，使用键值对
     *
     * @param map
     * @return
     */
    List<User> findByForEachMap1(@Param("map")Map<String,Long> map);

    /**
     * 参数类型为Map时，使用值集合
     *
     * @param map
     * @return
     */
    List<User> findByForEachMap2(@Param("map")Map<String,Long> map);

    /**
     * 参数类型为Map时，使用键集合
     *
     * @param map
     * @return
     */
    List<User> findByForEachMap3(@Param("map")Map<String,Long> map);


    // ============================== selectKey =================================

    /**
     * selectKey 标签作用1：获取自增主键
     *  自增id塞进user主键字段
     * @return 新增条数
     */
    int addUserForGetId(User user);

    /**
     * selectKey 标签作用2：自定义主键的生成方式
     * @param user
     * @return
     */
    int addUserForGenerateId(User user);

    // ======================== sql 标签的使用 ===============

    /**
     * sql标签的基本使用
     *
     * @param id
     * @return
     */
    User findForBaseSql(Long id);

    /**
     * sql标签的静态参数化
     * @param id
     * @return
     */
    User findForParamSql(Long id);


}
