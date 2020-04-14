package concurrent.D;

/**
 * SafeLazyInitialization
 * <p/>
 * Thread-safe lazy initialization
 * 线程安全
 * 添加锁
 * @author Brian Goetz and Tim Peierls
 */
public class P_SafeLazyInitialization {
    private static Resource resource;

    public synchronized static Resource getInstance() {
        if (resource == null)
            resource = new Resource();
        return resource;
    }

    static class Resource {
    }
}
