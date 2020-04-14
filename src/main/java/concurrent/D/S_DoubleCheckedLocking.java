package concurrent.D;

/**
 * DoubleCheckedLocking
 * <p/>
 * Double-checked-locking antipattern
 * 线程不安全
 * 双重检查加锁(DCL)
 * 获取一个已构造好的Resource引用并没有使用同步,可能会造成一个仅被部分构造的Resource
 * @author Brian Goetz and Tim Peierls
 */
public class S_DoubleCheckedLocking {
    private static Resource resource;

    public static Resource getInstance() {
        if (resource == null) {
            synchronized (S_DoubleCheckedLocking.class) {
                if (resource == null)
                    resource = new Resource();
            }
        }
        return resource;
    }

    static class Resource {

    }
}
