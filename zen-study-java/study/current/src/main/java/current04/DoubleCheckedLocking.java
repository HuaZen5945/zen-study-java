package current04;

/**
 * @program: zen-study
 * @description: 双重检查锁
 * @author: HUA
 * @create: 2023-02-27 06:27
 **/
public class DoubleCheckedLocking {   // 1
    private static Instance instance;  // 2

    public static Instance getInstance() { // 3
        if (instance == null) {             // 4 第一次检查
            synchronized (DoubleCheckedLocking.class) {// 5 加锁
                if (instance == null) {  // 6 第二次检查
                    instance = new Instance();      // 7 问题根源出在这里
                } // 8
            }  //9
        } // 10
        return instance; // 11
    } // 12
}