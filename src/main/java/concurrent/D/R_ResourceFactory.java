package concurrent.D;


/**
 * ResourceFactory
 * <p/>
 * Lazy initialization holder class idiom
 * 线程安全
 * 延长初始化占位类模式
 * jvm推迟ResourceHolder初始化操作,直到第一次用才加载,由于使用静态初始化,因此不需要额外同步
 * @author Brian Goetz and Tim Peierls
 */
public class R_ResourceFactory {
    private static class ResourceHolder {
        public static Resource resource = new Resource();
    }

    public static Resource getResource() {
        return ResourceHolder.resource;
    }

    static class Resource {
    }
}
