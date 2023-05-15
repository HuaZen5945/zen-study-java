package current02;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

public class SyncSyncLockRelease2 {
    // -XX:BiasedLockingStartupDelay=0  配置0,关闭延迟开启偏向锁
    public static void main(String[] args) throws InterruptedException {
        List<Object> list = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            Object obj = new Object(); //创建20个object对象
            list.add(obj);
            // ====A 加锁前===: 都是101 偏向锁，其他位为0
            System.out.println("====A 加锁前==="+ClassLayout.parseInstance(obj).toPrintable());
            Thread A = new Thread() {
                @SneakyThrows
                @Override
                public void run() { //创建了20条线程
                    synchronized (obj) {
                        // ===A 加锁中=== : 都是101 偏向锁，其他位为线程A的线程id
                        System.out.println("===A 加锁中==="+ClassLayout.parseInstance(obj).toPrintable());
                        Thread.sleep(2000);
                    }
//                    Thread.sleep(20000);
                }
            };
            A.start();
        }
        Thread.sleep(200);

        for(int i = 0; i < 20; i++) {
            Object obj = list.get(i);
            // ====B加锁前===: 都是101 偏向锁，其他位为线程A的线程id
            System.out.println("====B加锁前==="+ClassLayout.parseInstance(obj).toPrintable());
            Thread B = new Thread() {
                @SneakyThrows
                @Override
                public void run() {
                    synchronized (obj) {
                        // ====B加锁中===: 都是010 重量级锁，其他位为线程B的线程id
                        System.out.println("====B加锁中==="+ClassLayout.parseInstance(obj).toPrintable());
                        Thread.sleep(1000);
                    }
                }
            };
            B.start();
        }

        Thread.sleep(5000);
        synchronized (list.get(19)) {
            // 差别： 000 轻量级锁
            System.out.println("===再次加锁==="+ClassLayout.parseInstance(list.get(19)).toPrintable());
        }
        // ===新对象== ： 101 轻量级锁
        System.out.println("===新对象=="+ClassLayout.parseInstance(new Object()).toPrintable());
    }
}