package concurrent.D;


/**
 * EagerInitialization
 * <p/>
 * Eager initialization
 * 线程安全
 * 提前初始化
 * @author Brian Goetz and Tim Peierls
 */
public class Q_EagerInitialization {
    private static Resource resource = new Resource();

    public static Resource getResource() {
        return resource;
    }

    static class Resource {
    }
}
