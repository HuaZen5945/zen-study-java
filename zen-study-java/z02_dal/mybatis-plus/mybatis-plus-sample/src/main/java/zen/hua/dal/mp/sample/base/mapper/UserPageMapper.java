package zen.hua.dal.mp.sample.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import zen.hua.dal.mp.sample.base.entity.User;
import zen.hua.dal.mp.sample.base.model.MyPage;
import zen.hua.dal.mp.sample.base.model.ParamSome;
import zen.hua.dal.mp.sample.base.model.UserChildren;

import java.util.List;
import java.util.Map;

/**
 * @author miemie
 * @since 2018-08-10
 */
@Mapper
public interface UserPageMapper extends BaseMapper<User> {

    /**
     * 3.x 的 page 可以进行取值,多个入参记得加上注解
     * 自定义 page 类必须放在入参第一位
     * 返回值可以用 IPage<T> 接收 也可以使用入参的 MyPage<T> 接收
     * <li> 3.1.0 之前的版本使用注解会报错,写在 xml 里就没事 </li>
     * <li> 3.1.0 开始支持注解,但是返回值只支持 IPage ,不支持 IPage 的子类</li>
     *
     * @param myPage 自定义 page
     * @return 分页数据
     */
//    @Select("select * from user where (age = #{pg.selectInt} and name = #{pg.selectStr}) or (age = #{ps.yihao} and name = #{ps.erhao})")
    MyPage<User> mySelectPage(@Param("pg") MyPage<User> myPage, @Param("ps") ParamSome paramSome);


    @ResultMap("userChildrenMap")
    @Select("<script>select u.id,u.name,u.email,u.age,c.id as \"c_id\",c.name as \"c_name\",c.user_id as \"c_user_id\" " +
            "from user u " +
            "left join children c on c.user_id = u.id " +
            "<where>" +
            "<if test=\"selectInt != null\"> " +
            "and u.age = #{selectInt} " +
            "</if> " +
            "<if test=\"selectStr != null and selectStr != ''\"> " +
            "and c.name = #{selectStr} " +
            "</if> " +
            "</where>" +
            "</script>")
    MyPage<UserChildren> userChildrenPage(MyPage<UserChildren> myPage);

    /**
     * 如果返回类型是 IPage 则入参的 IPage 不能为null,因为 返回的IPage == 入参的IPage;
     * 如果想临时不分页,可以在初始化IPage时size参数传 <0 的值;
     * @param pg
     * @param map
     * @return
     */
    MyPage<User> mySelectPageMap(@Param("pg") MyPage<User> pg, @Param("map") Map<String, Object> map);


    /**
     *  如果返回类型是 List 则入参的 IPage 可以为 null(为 null 则不分页),但需要你手动 入参的IPage.setRecords(返回的 List);
     *  如果 xml 需要从 page 里取值,需要 page.属性 获取
     * @param myPage
     * @return
     */
    List<User> myPageSelect(MyPage<User> myPage);
}
