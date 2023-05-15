package current02;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class SyncSyncLockRelease {
    static Thread A;
    static Thread B;
    // -XX:BiasedLockingStartupDelay=0  配置0延迟开启偏向锁
    public static void main(String[] args) throws InterruptedException {
        final List<Object> list = new ArrayList<>();
        A = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                Object a = new Object();
                list.add(a);
                // AAAA加锁前:  101  偏向锁，其他位为0
                System.out.println("AAAA加锁前" + ClassLayout.parseInstance(a).toPrintable());
                synchronized (a) {
                    // AAAA加锁中: 101 偏向锁，其他位是 线程A的id
                    System.out.println("AAAA加锁中" + ClassLayout.parseInstance(a).toPrintable());
                }
                // AAAA加锁后:  101 偏向锁，其他位是 线程A的id
                System.out.println("AAAA加锁后" + ClassLayout.parseInstance(a).toPrintable());
                //防止竞争 执行完后唤醒线程B/ 确保A线程 死亡 Terminated
//                LockSupport.unpark(B);
            }
        };
        B = new Thread() {
            @Override
            public void run() {
//                LockSupport.park();
                while (A.isAlive()) {

                }
                Object a = list.get(0);
                // 线程BBBB加锁前: 101 偏向锁，其他位是 线程A的id
                System.out.println("线程BBBB加锁前" + ClassLayout.parseInstance(a).toPrintable());
                synchronized (a) {
                    // 线程BBBB加锁中: 000 轻量级锁，其他位是线程B的id
                    System.out.println("线程BBBB加锁中" + ClassLayout.parseInstance(a).toPrintable());
                }
                // 线程BBBB加锁后: 001 无锁，其他位为0
                // sync锁并不能降级，降级的话应该是变为轻量级，而不是变为无锁。降级不好把握临界点。
                System.out.println("线程BBBB加锁后" + ClassLayout.parseInstance(a).toPrintable());
                // 新产生的对象: 101  偏向锁，其他位为0
                System.out.println("新产生的对象" + ClassLayout.parseInstance(new Object()).toPrintable());
            }
        };
        A.start();
        B.start();
        // 都释放锁后，并结束生命后
        Thread.sleep(2000);
        // ====不再竞争锁=== : 001 无锁
        System.out.println("====不再竞争锁==="+ClassLayout.parseInstance(list.get(0)).toPrintable());
        synchronized (list.get(0)) {
            System.out.println("====竞争锁==="+ClassLayout.parseInstance(list.get(0)).toPrintable());
        }
    }
}