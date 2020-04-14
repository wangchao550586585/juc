package concurrent.D;

import java.util.concurrent.locks.*;

/**
 * SemaphoreOnLock
 * <p/>
 * Counting semaphore implemented using Lock
 * (Not really how java.util.concurrent.Semaphore is implemented)
 * 线程安全
 * 使用Lock实现信号量
 * @author Brian Goetz and Tim Peierls
 */
public class H_SemaphoreOnLock {
    private final Lock lock = new ReentrantLock();
    // CONDITION PREDICATE: permitsAvailable (permits > 0)
    private final Condition permitsAvailable = lock.newCondition();
    private int permits;

    H_SemaphoreOnLock(int initialPermits) {
        lock.lock();
        try {
            permits = initialPermits;
        } finally {
            lock.unlock();
        }
    }

    // BLOCKS-UNTIL: permitsAvailable
    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits <= 0)
                permitsAvailable.await();
            --permits;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            ++permits;
            permitsAvailable.signal();
        } finally {
            lock.unlock();
        }
    }
}
