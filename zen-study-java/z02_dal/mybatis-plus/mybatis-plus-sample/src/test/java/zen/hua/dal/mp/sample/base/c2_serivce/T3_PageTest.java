package zen.hua.dal.mp.sample.base.c2_serivce;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.dal.mp.sample.base.BaseTest;
import zen.hua.dal.mp.sample.base.entity.User;
import zen.hua.dal.mp.sample.base.service.IUserService;

/**
 * @program: zen-work
 * @description:
 * @author: HUA
 * @create: 2023-02-23 22:42
 **/
public class T3_PageTest extends BaseTest {

    @Autowired
    protected IUserService userService;

    @Test
    public void lambdaPageTest() {
        LambdaQueryChainWrapper<User> wrapper2 = userService.lambdaQuery();
        wrapper2.like(User::getName, "a");
        userService.page(new Page<>(1, 10), wrapper2.getWrapper()).getRecords().forEach(System.out::print);
    }

    @Test
    public void test() {
        userService.lambdaQuery().like(User::getName, "a").list().forEach(System.out::println);

        Page page = userService.lambdaQuery().like(User::getName, "a").page(new Page<>(1, 10));
        page.getRecords().forEach(System.out::println);
    }
}
