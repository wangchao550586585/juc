package concurrent.D.E;


/**
 * GrumpyBoundedBuffer
 * <p/>
 * Bounded buffer that balks when preconditions are not met
 * 线程安全
 * 当不满足前提条件时,有界缓存不会执行相应操作
 * @author Brian Goetz and Tim Peierls
 */
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    public GrumpyBoundedBuffer() {
        this(100);
    }

    public GrumpyBoundedBuffer(int size) {
        super(size);
    }

    public synchronized void put(V v) throws BufferFullException {
        if (isFull())
            throw new BufferFullException();
        doPut(v);
    }

    public synchronized V take() throws BufferEmptyException {
        if (isEmpty())
            throw new BufferEmptyException();
        return doTake() ;
    }
}

/**
 * 1:调用失败,休眠一段时间在此尝试,由于休眠会造成低响应性
 * 2:重复访问(忙等待or自旋等待),消耗大量CPU时间
 * 3:Thread.yield,让出一定时间让另一个线程执行,提升响应
 *
 */
class ExampleUsage {
    private GrumpyBoundedBuffer<String> buffer;
    int SLEEP_GRANULARITY = 50;

    void useBuffer() throws InterruptedException {
        while (true) {
            try {
                String item = buffer.take();
                // use item
                break;
            } catch (BufferEmptyException e) {
                Thread.sleep(SLEEP_GRANULARITY);
            }
        }
    }
}

class BufferFullException extends RuntimeException {
}

class BufferEmptyException extends RuntimeException {
}
