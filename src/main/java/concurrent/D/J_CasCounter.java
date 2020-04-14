package concurrent.D;

/**
 * CasCounter
 * <p/>
 * Nonblocking counter using CAS
 * 线程安全
 * 基于CAS实现的非阻塞计数器
 * @author Brian Goetz and Tim Peierls
 */
public class J_CasCounter {
    private J_SimulatedCAS value;

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        } while (v != value.compareAndSwap(v, v + 1));
        return v + 1;
    }
}
