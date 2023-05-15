package zen.hua.dal.mp.sample.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("company")
public class Company {
	private Long id;
    private String name;
    List<User> userList;
}
