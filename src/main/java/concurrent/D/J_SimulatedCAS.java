package concurrent.D;

/**
 * SimulatedCAS
 * <p/>
 * Simulated CAS operation
 * 线程安全
 * 模拟CAS操作
 * @author Brian Goetz and Tim Peierls
 */
public class J_SimulatedCAS {
    private int value;

    public synchronized int get() {
        return value;
    }

    public synchronized int compareAndSwap(int expectedValue,
                                           int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue)
            value = newValue;
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue,
                                              int newValue) {
        return (expectedValue
                == compareAndSwap(expectedValue, newValue));
    }
}
