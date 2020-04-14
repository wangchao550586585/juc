package concurrent.B.ZE;

import java.util.concurrent.*;

/**
 * ValueLatch
 * <p/>
 * Result-bearing latch used by ConcurrentPuzzleSolver
 * <p>
 * 线程安全
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ValueLatch<T> {
    private T value = null;
    private final CountDownLatch done = new CountDownLatch(1);

    public boolean isSet() {
        return (done.getCount() == 0);
    }

    public synchronized void setValue(T newValue) {
        if (!isSet()) {
            value = newValue;
            done.countDown();
        }
    }

    public T getValue() throws InterruptedException {
        done.await();
        synchronized (this) {
            return value;
        }
    }
}
