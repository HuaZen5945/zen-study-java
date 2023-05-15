package zen.hua.mybatis.boot.sample.base.config.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  介绍：http://www.mybatis.cn/mybatis/34.html
 * <pre>
 *     通过类型处理器的泛型，MyBatis可以得知该类型处理器的Java类型，不过这种行为可以通过两种方法改变：
 *     1. 在类型处理器的配置元素（typeHandler element）上增加一个javaType属性。比如，javaType="String"
 *     2. 在类型处理器的类上（TypeHandler class）增加一个@MappedTypes注解来指定与其关联的Java类型列表。
 *      如果在javaType属性中也设置了，则注解方式将被忽略。
 *
 *    可以通过两种方式来指定被关联的JDBC类型：
 *    1. 在类型处理器的配置元素上增加一个jdbcType属性。比如：javaType="VARCHAR"
 *    2. 在类型处理器的类上（TypeHandler class）增加一个@MappedJdbcTypes注解来指定与其关联的JDBC类型列表。
 *      如果在javaType属性中也同时指定，则注解方式将被忽略。
 *
 *     注意：
 *     使用这个的类型处理器将会覆盖已经存在的处理Java的String类型属性和VARCHAR参数及结果的类型处理器。
 *     要注意MyBatis不会窥探数据库元信息来决定使用哪种类型，所以必须在参数和结果映射中指明是VARCHAR类型字段，
 *     以使其能绑定到正确的类型处理器上。这是因为MyBatis直到语句被执行才清楚数据类型。
 * </pre>
 */
//ExampleTypeHandler.java
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ExampleTypeHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException
  { 
    ps.setString(i,parameter);
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName) throws SQLException
  { 
    return rs.getString(columnName);
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException
  { 
    return rs.getString(columnIndex);
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException
  {
    return cs.getString(columnIndex);
  }
}