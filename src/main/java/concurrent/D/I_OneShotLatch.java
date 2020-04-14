package concurrent.D;

import java.util.concurrent.locks.*;


/**
 * OneShotLatch
 * <p/>
 * Binary latch using AbstractQueuedSynchronizer
 * 线程安全
 *  使用AbstractQueuedSynchronizer实现的二元闭锁
 * @author Brian Goetz and Tim Peierls
 */
public class I_OneShotLatch {
    private final Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        protected int tryAcquireShared(int ignored) {
            // Succeed if latch is open (state == 1), else fail
            //如果闭锁是开的
            return (getState() == 1) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int ignored) {
            //打开闭锁
            setState(1); // Latch is now open
            return true; // Other threads may now be able to acquire

        }
    }
}
