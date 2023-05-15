package redis.boot.sample.t2_aop.service;

import cn.hutool.core.collection.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.boot.sample.t2_aop.anno.RedisCacheAop;

import java.util.List;

/**
 * @program: zen-study-java
 * @description: 基础查询服务
 * @author: HUA
 * @create: 2023-05-15 22:50
 **/
@Slf4j
@Service
public class BaseService {

    @RedisCacheAop(expireTime = "5")
    public List<String> queryList(String param) {
        if("1".equals(param)) {
            log.info("[BaseService#queryList#1] ----- 没走缓存");
            return ListUtil.toList("12","13","14");
        }
        else if("2".equals(param)) {
            log.info("[BaseService#queryList#2] ----- 没走缓存");
            return ListUtil.toList("22","23","24");
        }
        log.info("[BaseService#queryList#other] ----- 没走缓存");
        return null;
    }
}
