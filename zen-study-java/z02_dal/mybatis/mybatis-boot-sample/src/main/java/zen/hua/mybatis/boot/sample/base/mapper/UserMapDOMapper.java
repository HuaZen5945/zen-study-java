package zen.hua.mybatis.boot.sample.base.mapper;


import org.apache.ibatis.annotations.MapKey;
import zen.hua.mybatis.boot.sample.base.entity.UserMapDO;

import java.util.List;
import java.util.Map;

public interface UserMapDOMapper {

    @MapKey(value = "id")
    List<Map<String,String>> queryMap();

    /**
     * https://blog.csdn.net/weixin_36245032/article/details/115882847
     *
     * distinct 只能返回它想要的字段，不能返回其他字段。
     * group by 使指定列不重复，其他列可以重复(ang(column))。
     *
     * 注意: mysql 5.7 及其以上版本不支持
     *
     * @return
     */
    List<UserMapDO> queryDistinct();

    /**
     * mysql 5.7 及其以上版本配合GROUP BY 关键字一起使用，查询非分组字段可以使用any_value 函数
     * @return
     */
    List<UserMapDO> queryDistinct2();
}