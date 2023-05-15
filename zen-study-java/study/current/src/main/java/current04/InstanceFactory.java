package current04;

/**
 * @program: zen-work
 * @description:
 * @author: HUA
 * @create: 2023-02-27 07:04
 **/
public class InstanceFactory {
    private static class InstanceHolder {
        public static Instance instance = new Instance();
    }

    public static Instance getInstance() {
        // 这里将导致InstanceHolder类被初始化
        return InstanceHolder.instance;
    }
}
