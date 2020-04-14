package concurrent.A;


/**
 * 线程安全
 */
public class J_SynchronizedInteger {
    private int value;

    public synchronized int get() {
        return value;
    }

    public synchronized void set(int value) {
        this.value = value;
    }
}
