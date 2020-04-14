package concurrent.D;

import java.util.concurrent.locks.*;

/**
 * F_ConditionBoundedBuffer
 * <p/>
 * Bounded buffer using explicit condition variables
 * 线程安全
 * 使用显示条件变量的有界缓存
 * 将2个谓词条件分开并放到2个等待线程集中,Condition更容易满足单次通知的需求,极大减少在每次缓存操作中发生的上下文切换与锁请求
 * @author Brian Goetz and Tim Peierls
 */
public class G_ConditionBoundedBuffer<T> {
    protected final Lock lock = new ReentrantLock();
    // CONDITION PREDICATE: notFull (count < items.length)
    //非满谓词
    private final Condition notFull = lock.newCondition();
    // CONDITION PREDICATE: notEmpty (count > 0)
    //非空谓词
    private final Condition notEmpty = lock.newCondition();
    private static final int BUFFER_SIZE = 100;
    private final T[] items = (T[]) new Object[BUFFER_SIZE];
    private int tail, head, count;

    // BLOCKS-UNTIL: notFull
    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[tail] = x;
            if (++tail == items.length)
                tail = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    // BLOCKS-UNTIL: notEmpty
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            T x = items[head];
            items[head] = null;
            if (++head == items.length)
                head = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
