package redis.boot.sample.t1_base.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.boot.sample.biz.RedisBootUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@RestController("/base")
public class BaseController {


    ReentrantLock reentrantLock = new ReentrantLock();

    /**
     * 测试单机锁
     * 将启动类设为多例启动
     */
    //测试：http://localhost:10088/redis/lock/huanzi
    //测试：http://localhost:10089/redis/lock/huanzi
    @RequestMapping("/redis/lock/{key}")
    public String lock(@PathVariable("key") String key){
        try {
            System.out.println(Thread.currentThread().getId()+"_1");
            boolean b = reentrantLock.tryLock(15, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getId()+"_2");
            if(b) {
                System.out.println(Thread.currentThread().getId()+"_3");
                Thread.sleep(10000);
            }
            System.out.println(Thread.currentThread().getId()+"_4");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getId()+"_5");
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getId()+"_6");
            reentrantLock.unlock();
            System.out.println(Thread.currentThread().getId()+"_7");
        }

        return key;
    }

    /**
     * 测试分布式锁
     */
    //测试：http://localhost:10088/redis/lock2/huanzi
    //测试：http://localhost:10089/redis/lock2/huanzi
    @RequestMapping("/redis/lock2/{key}")
    public String lock2(@PathVariable("key") String key){

        try {
            System.out.println(Thread.currentThread().getId()+"_1");
            boolean b = RedisBootUtil.LockOps.getLockUntilTimeout(key,key,10, TimeUnit.SECONDS,15000);
            System.out.println(Thread.currentThread().getId()+"_2");
            if(b) {
                System.out.println(Thread.currentThread().getId()+"_3");
                Thread.sleep(10000);
            }
            System.out.println(Thread.currentThread().getId()+"_4");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getId()+"_5");
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getId()+"_6");
            RedisBootUtil.LockOps.releaseLock(key,key);
            System.out.println(Thread.currentThread().getId()+"_7");
        }
        return key;
    }

//    /**
//     * 发布消息
//     */
//    //测试：http://localhost:10088/redis/eventPush
//    @RequestMapping("/redis/eventPush")
//    public Boolean eventPush(){
//        template.convertAndSend("topic1","topic1-我是第一种事件消息");
//        template.convertAndSend("topic2","topic2-我是第二种事件消息");
//        return true;
//    }
}
