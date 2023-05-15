package zen.hua.dal.mp.sample.base.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import zen.hua.dal.mp.sample.base.entity.Children;
import zen.hua.dal.mp.sample.base.entity.User;

import java.util.List;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserChildren extends User {

    private List<Children> c;
}
