package concurrent.D;

/**
 * UnsafeLazyInitialization
 * <p/>
 * Unsafe lazy initialization
  线程不安全
 * @author Brian Goetz and Tim Peierls
 */
public class O_UnsafeLazyInitialization {
    private static Resource resource;

    public static Resource getInstance() {
        if (resource == null)
            resource = new Resource(); // unsafe publication
        return resource;
    }

    static class Resource {
    }
}
