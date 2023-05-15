package current01;

import lombok.SneakyThrows;

public class JoinRelase {
    static Object object = new Object();
    public static void main(String[] args) throws InterruptedException {

        for(int i = 0; i < 2; i++) {
            Thread thread = new Thread(new SubThread(),"Daemon Thread!"+i);
            thread.setName("thread-" + i);
            thread.start();
            Thread.sleep(100);
        }
    }
    static class SubThread implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            // 情况一：打印一次
            synchronized (object) {
            // 情况二: 打印两次
//            synchronized (Thread.currentThread()) {
                System.out.println("获取到锁！！！ThreadName: " + Thread.currentThread().getName());
                Thread.currentThread().join();
                // 不会执行？？
                System.out.println("代码执行完成！！！ThreadName: " + Thread.currentThread().getName());
            }
        }
    }
}

