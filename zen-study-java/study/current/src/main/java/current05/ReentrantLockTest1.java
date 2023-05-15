package current05;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: zen-work
 * @description:
 * @author: HUA
 * @create: 2023-02-28 07:14
 **/
public class ReentrantLockTest1 {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        /*
         * AQS-compareAndSetState
         * acquire
         * addWaiter
         * enq
         * acquireQueued
         * shouldParkAfterFailedAcquire
         * parkAndCheckInterrupt
         * cancelAcquire
         * */
        lock.lock();
    }
}
