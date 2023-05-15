package redis.boot.sample.t2_aop.config;

import cn.hutool.core.collection.CollUtil;

import java.util.List;

/**
 * @program: zen-work
 * @description: 缓存动态配置类(实际开发中用)
 * @author: HUA
 * @create: 2023-02-11 15:15
 **/
public class CacheSwitch {

    /**
     * 是否开启redis缓存
     * @return
     */
    public static boolean openRedisCache() {
        return true;
    }

    /**
     * 哪些方法不开启缓存
     * 格式：全限定类名#方法名
     * @return
     */
    public static List<String> closeCacheMethods() {
        return CollUtil.newArrayList();
    }
}
