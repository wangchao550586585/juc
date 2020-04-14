package concurrent.C.M;


import java.util.concurrent.Semaphore;

/**
 * BoundedBuffer
 * <p/>
 * 基于信号量的有界缓存
 * 可以用ArrayBlockingQueue/LinkedBlockingQueue实现
 * <p>
 * 线程安全
 *
 * @author Brian Goetz and Tim Peierls
 */
public class M_SemaphoreBoundedBuffer<E> {
    private final Semaphore availableItems, availableSpaces;
    private final E[] items;
    private int putPosition = 0, takePosition = 0;

    public M_SemaphoreBoundedBuffer(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E x) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(x);
        availableItems.release();
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E item = doExtract();
        availableSpaces.release();
        return item;
    }


    private synchronized E doExtract() {
        int i = takePosition;
        E x = items[i];
        /*
        防止资源泄漏,显示设置null
         */
        items[i] = null;
        takePosition = (++i == items.length) ? 0 : i;
        return x;
    }

    private synchronized void doInsert(E x) {
        int i = putPosition;
        items[i] = x;
        putPosition = (++i == items.length) ? 0 : i;
    }
}

