package zen.hua.dal.mp.biz.outbound.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.dal.mp.biz.outbound.BizOutBoundApplicationTests;
import zen.hua.dal.mp.biz.outbound.mannager.WorkingDayManager;

/**
 * @program: zen-study
 * @description:
 * @author: HUA
 * @create: 2023-02-11 11:14
 **/
public class WorkingDayTest extends BizOutBoundApplicationTests {

    @Autowired
    private WorkingDayManager workingDayManager;

    @Test
    public void testInsert() {
        Integer day = 2023011;
        Integer type = 2;
        workingDayManager.saveData(day,type);
    }
}
