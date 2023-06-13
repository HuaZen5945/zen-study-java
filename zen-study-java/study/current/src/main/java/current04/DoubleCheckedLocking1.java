package current04;

/**
 * @program: zen-study
 * @description: 双重检查锁
 * @author: HUA
 * @create: 2023-02-27 06:27
 **/
public class DoubleCheckedLocking1 {
    private volatile static Instance instance;

    public static Instance getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedLocking1.class) {
                if (instance == null) {
                    instance = new Instance(); // instance为volatile，现在没问题了。
                }
            }
        }
        return instance;
    }
}