package zen.hua.dal.mp.sample.base.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import zen.hua.dal.mp.sample.base.entity.User;
import zen.hua.dal.mp.sample.base.mapper.UserMapper;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements IUserService {

}
