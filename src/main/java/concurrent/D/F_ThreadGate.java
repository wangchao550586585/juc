package concurrent.D;

/**
 * F_ThreadGate
 * <p/>
 * Recloseable gate using wait and notifyAll
 * 线程安全
 * 使用wait/notifyAll实现可重新关闭的阀门
 * @author Brian Goetz and Tim Peierls
 */
public class F_ThreadGate {
    // CONDITION-PREDICATE: opened-since(n) (isOpen || generation>n)
    private boolean isOpen;
    private int generation;

    public synchronized void close() {
        isOpen = false;
    }

    public synchronized void open() {
        ++generation;
        isOpen = true;
        notifyAll();
    }

    // BLOCKS-UNTIL: opened-since(generation on entry)
    public synchronized void await() throws InterruptedException {
        int arrivalGeneration = generation;
        while (!isOpen && arrivalGeneration == generation)
            wait();
    }
}
