package concurrent.B;

import java.util.concurrent.*;

/**
 * MyThreadFactory
 * <p/>
 * Custom thread factory
 * @author Brian Goetz and Tim Peierls
 */
public class ZB_MyThreadFactory implements ThreadFactory {
    private final String poolName;

    public ZB_MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    public Thread newThread(Runnable runnable) {
        return new ZB_MyAppThread(runnable, poolName);
    }
}
