package zen.hua.dal.mp.sample.base.c4_ext;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zen.hua.dal.mp.sample.base.BaseTest;
import zen.hua.dal.mp.sample.base.entity.LogicData;
import zen.hua.dal.mp.sample.base.mapper.LogicDataMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: zen-work
 * @description: 测试逻辑删除
 * @author: HUA
 * @create: 2023-02-23 23:55
 *
 **/
@Slf4j
public class T1_LogicDeleteTest extends BaseTest {

    @Autowired
    private LogicDataMapper logicDataMapper;

    /**
     *
     */
    @Test
    public void tCommon() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            LogicData common = new LogicData().setName("" + i).setDeleted(0);
            logicDataMapper.insert(common);
            ids.add(common.getId());
        }
        // SET deleted=1 WHERE deleted=0
        // SET delete_time=now() WHERE delete_time IS NULL
        log.error("------------------------------------------------deleteById--------------------------------------------------------");
        logicDataMapper.deleteById(ids.remove(0));
        log.error("------------------------------------------------deleteByMap--------------------------------------------------------");
        logicDataMapper.deleteByMap(Maps.newHashMap("id", ids.remove(0)));
        log.error("------------------------------------------------delete--------------------------------------------------------");
        logicDataMapper.delete(Wrappers.<LogicData>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------deleteBatchIds--------------------------------------------------------");
        logicDataMapper.deleteBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));


        // WHERE AND deleted=0 ……
        // WHERE delete_time IS NULL
        log.error("------------------------------------------------updateById--------------------------------------------------------");
        logicDataMapper.updateById(new LogicData().setId(ids.remove(0)).setName("老王"));
        log.error("------------------------------------------------update--------------------------------------------------------");
        logicDataMapper.update(new LogicData().setName("老王"), Wrappers.<LogicData>update().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectById--------------------------------------------------------");
        logicDataMapper.selectById(ids.remove(0));
        log.error("------------------------------------------------selectBatchIds--------------------------------------------------------");
        logicDataMapper.selectBatchIds(Arrays.asList(ids.remove(0), ids.remove(0)));
        log.error("------------------------------------------------selectByMap--------------------------------------------------------");
        logicDataMapper.selectByMap(Maps.newHashMap("id", ids.remove(0)));
        log.error("------------------------------------------------selectOne--------------------------------------------------------");
        logicDataMapper.selectOne(Wrappers.<LogicData>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectCount--------------------------------------------------------");
        logicDataMapper.selectCount(Wrappers.<LogicData>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectList--------------------------------------------------------");
        logicDataMapper.selectList(Wrappers.<LogicData>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectMaps--------------------------------------------------------");
        logicDataMapper.selectMaps(Wrappers.<LogicData>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectObjs--------------------------------------------------------");
        logicDataMapper.selectObjs(Wrappers.<LogicData>query().select("id").eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectPage--------------------------------------------------------");
        logicDataMapper.selectPage(new Page<>(), Wrappers.<LogicData>query().eq("id", ids.remove(0)));
        log.error("------------------------------------------------selectMapsPage--------------------------------------------------------");
        logicDataMapper.selectMapsPage(new Page<>(), Wrappers.<LogicData>query().eq("id", ids.remove(0)));
    }
}
