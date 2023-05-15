package current02;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;

public class LightLock {
    // 关闭偏向锁：-XX:BiasedLockingStartupDelay=1000000
    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        // 无锁
        System.out.println("====A 加锁前==="+ClassLayout.parseInstance(obj).toPrintable());
        Thread A = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (obj) {
                    // 轻量级锁
                    System.out.println("===A 加锁中==="+ClassLayout.parseInstance(obj).toPrintable());
                    Thread.sleep(2000);
                }

            }
        };
        A.start();
        Thread.sleep(500);
        // 轻量级锁
        System.out.println("====B加锁前==="+ClassLayout.parseInstance(obj).toPrintable());
        Thread B = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (obj) {
                    // 重量级锁
                    System.out.println("====B加锁中==="+ClassLayout.parseInstance(obj).toPrintable());
                    Thread.sleep(1000);
                }
            }
        };
        B.start();
        Thread.sleep(5000);
        synchronized (obj) {
            // 重量级锁
            System.out.println("====再次加锁中==="+ClassLayout.parseInstance(obj).toPrintable());
        }

        Object objNew = new Object();
        synchronized (objNew) {
            // 轻量级锁
            System.out.println("====新对象加锁中==="+ClassLayout.parseInstance(objNew).toPrintable());
        }
    }

}