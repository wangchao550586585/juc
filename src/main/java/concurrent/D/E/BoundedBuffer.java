package concurrent.D.E;


/**
 * BoundedBuffer
 * <p/>
 * Bounded buffer using condition queues
 * <p>
 * 线程安全
 * 使用条件队列实现的有界缓存
 * 条件谓词(缓存不为空,缓存不满)中包含多个状态变量，而状态变量由一个锁保护,因此在测试谓词之前需要持有这个锁，锁对象与条件队列对象(调用wait/notify等方法的对象)必须是同一个对象
 *
 * @author Brian Goetz and Tim Peierls
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    // CONDITION PREDICATE: not-full (!isFull())
    // CONDITION PREDICATE: not-empty (!isEmpty())
    public BoundedBuffer() {
        this(100);
    }

    public BoundedBuffer(int size) {
        super(size);
    }

    // BLOCKS-UNTIL: not-full
    public synchronized void put(V v) throws InterruptedException {
        //线程在条件谓词不为真的情况下也可以反复醒来,所以while
        while (isFull())
            //释放锁,阻塞当前线程,等待直到超时,线程被终端Or唤醒
            wait();
        doPut(v);
        notifyAll();
    }

    // BLOCKS-UNTIL: not-empty
    public synchronized V take() throws InterruptedException {
        while (isEmpty())
            wait();
        V v = doTake();
        notifyAll();
        return v;
    }

    /**
     * 条件通知:没填满以及不为空,则通知。
     * 优化
     * @param v
     * @throws InterruptedException
     */
    // BLOCKS-UNTIL: not-full
    // Alternate form of put() using conditional notification
    public synchronized void alternatePut(V v) throws InterruptedException {
        while (isFull())
            wait();
        boolean wasEmpty = isEmpty();
        doPut(v);
        if (wasEmpty)
            notifyAll();
    }
}
