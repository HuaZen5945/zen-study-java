package current05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SelfLock implements Lock {

    // AQS 呢？如何使用呢？
    // AQS 中可以重写的方法，protected修饰，非final，一般抛异常，等待重写。
    // 自定义加锁状态，我们使state为0是未加锁，1是加锁
    private static class Sync extends AbstractQueuedSynchronizer {
        //加锁的时候用 state int 类型
        @Override
        public boolean tryAcquire(int acquires) {
                if(compareAndSetState(0, acquires)) {
                    // 设置当前线程为执行线程
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
                return false;
        }

        //解锁的
        @Override
        public boolean tryRelease(int releases) {
            // 0 是未加锁
            if(getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setState(0); // 不用，当前线程释放锁，说明，当前线程持有锁。
            return true;
        }

        //创建condition. wait notify
        Condition newCondition(){
            return new ConditionObject();
        }

        public boolean isLocked(){
            //锁定的话，state == 1
            return getState() == 1;
        }
    }
    // AQS实现
    private final Sync sync = new Sync();

    public void lock() {
//        sync.tryAcquire(); //调用错误
        sync.acquire(1); //传说中的模板方法吗？ 是的
    }
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        // 底层调 tryAcquire方法，传1
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }
    public void lockInterruptibly() throws InterruptedException {
        // 底层调 tryAcquire方法，传1
        sync.acquireInterruptibly(1);
    }
    public void unlock() {
        // 底层调 tryRelease(arg)方法，传1
        sync.release(1);
    }
    public Condition newCondition() {
        return sync.newCondition();
    }
    public boolean isLocked(){
        return sync.isLocked();
    }
    public boolean hasQueuedThreads(){
        return sync.hasQueuedThreads();
    }
}
