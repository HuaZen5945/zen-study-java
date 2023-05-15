package current01;

public class SleepRelaseCPU {
    public static void main(String[] args) {
        // 100个线程，如果不释放cpu的话，会急速飘升。结果正常低水平，说明释放。
        for(int i = 0; i < 100; i++) {
            Thread thread = new Thread(new SubThread(),"Daemon Thread!"+i);
            thread.start();
        }
    }
    static class SubThread implements Runnable {
        @Override
        public void run() {
            try {
                new Object().wait();
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(500000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("FINISH!");
            }
        }
    }
}

