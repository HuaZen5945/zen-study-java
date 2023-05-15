package current02;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;

public class SyncSyncLockRelease1 {
    // -XX:BiasedLockingStartupDelay=0  配置0延迟开启偏向锁
    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        // ====A 加锁前=== :  101  偏向锁，其他位为0
        System.out.println("====A 加锁前==="+ClassLayout.parseInstance(obj).toPrintable());
        Thread A = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (obj) {
                    // ===A 加锁中=== : 101 偏向锁，其他位是 线程A的id
                    System.out.println("===A 加锁中==="+ClassLayout.parseInstance(obj).toPrintable());
//                    Thread.sleep(2000);
                }
//                Thread.sleep(1000);

            }
        };
        A.start();
        while (A.isAlive()){}
        // ====B加锁前=== : 101 偏向锁，其他位是 线程A的id
        System.out.println("====B加锁前==="+ClassLayout.parseInstance(obj).toPrintable());
        Thread B = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                // 保证B线程执行时，A线程还在sleep。
                synchronized (obj) {
                    // 别人演示：010 重量级锁，其他为线程B的id。
                    // 自己实际：000 轻量级锁，其他位线程B的id。
                    System.out.println("====B加锁中==="+ClassLayout.parseInstance(obj).toPrintable());
//                    Thread.sleep(2000);
                }
            }
        };
        B.start();
        // 都释放锁后，并结束生命后
        Thread.sleep(5000);
        // ====不再竞争锁=== : 001 无锁
        System.out.println("====不再竞争锁==="+ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj) {
            System.out.println("====竞争锁==="+ClassLayout.parseInstance(obj).toPrintable());
        }
        System.out.println("====不再竞争锁2==="+ClassLayout.parseInstance(obj).toPrintable());
    }
}