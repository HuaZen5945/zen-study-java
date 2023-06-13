package zen.hua.dal.mp.biz.outbound.mannager;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;
import zen.hua.dal.mp.biz.outbound.entity.common.WorkingDayDO;
import zen.hua.dal.mp.biz.outbound.mapper.common.WorkingDayMapper;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @program: zen-study
 * @description: 工作日处理类
 * @author: HUA
 * @create: 2023-02-11 10:48
 **/
@Component
public class WorkingDayManager {

    @Resource
    private WorkingDayMapper workingDayMapper;

    /**
     * 获取指定工作日是哪一天
     * @param startDate 开始日期
     * @param workDays  工作日天数
     * @return
     */
    public Date getEndWorkDay(Date startDate, int workDays) {
        // 获取 日期部分
        SimpleDateFormat format = new SimpleDateFormat(DatePattern.PURE_DATE_PATTERN);
        Integer currDate = Integer.valueOf(format.format(startDate));
        // 获取时间部分
        String timeStr = DateUtil.formatTime(startDate);
        // 获取截止工作日
        Integer endDs = getEndDs(currDate,workDays);
        if(Objects.isNull(endDs)) {
            throw new RuntimeException("获取不到指定工作日");
        }
        return DateUtil.parse(String.format("%d %s",endDs,timeStr),"yyyyMMdd HH:mm:ss");
    }

    /**
     * 获取几个工作日后的某一天
     * @param ds
     * @param workDays
     * @return
     */
    public Integer getEndDs(Integer ds, int workDays) {
        QueryWrapper<WorkingDayDO> queryWrapper = new QueryWrapper<>();

        // SELECT ds FROM working_day WHERE ds >= {ds} AND type = 1 order by ds asc LIMIT workDays,1
        queryWrapper.ge("ds",ds)
                .eq("type",1)
                .orderByAsc("ds")
                .last(String.format("limit %d, 1",workDays))
                .select("ds");
        WorkingDayDO workingDayDO = workingDayMapper.selectOne(queryWrapper);
        return Optional.ofNullable(workingDayDO).orElseGet(WorkingDayDO::new).getDs();
    }

    /**
     * 保存或变更数据
     * @return
     */
    public boolean saveData(Integer ds, int type) {
        QueryWrapper<WorkingDayDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ds",ds);
        WorkingDayDO queryDO = workingDayMapper.selectOne(queryWrapper);
        WorkingDayDO  workingDayDO = WorkingDayDO.builder().ds(ds).type(type).build();
        if(Objects.isNull(queryDO)) {
            workingDayDO.setGmtCreate(new Date());
            workingDayDO.setGmtModified(new Date());
            workingDayMapper.insert(workingDayDO);
        } else {
            workingDayDO.setGmtModified(new Date());
            workingDayDO.setId(queryDO.getId());
            workingDayMapper.updateById(workingDayDO);
        }
        return true;
    }

}
