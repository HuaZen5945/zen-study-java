package current05;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    public static void main(String[] args) {
        LockSupport.park();
        LockSupport.park(new Object());
        LockSupport.unpark(new Thread());
    }
}
