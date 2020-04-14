package concurrent.C.M;


import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class M_BoundedBufferTest {


    private static final long LOCKUP_DETECT_TIMEOUT = 1000;
    private static final int CAPACITY = 10000;
    private static final int THRESHOLD = 10000;

    /**
     * 基本测试
     */
    @Test
    public void testIsEmptyWhenConstructed() {
        M_SemaphoreBoundedBuffer<Integer> bb = new M_SemaphoreBoundedBuffer<Integer>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    @Test
    public void testIsFullAfterPuts() throws InterruptedException {
        M_SemaphoreBoundedBuffer<Integer> bb = new M_SemaphoreBoundedBuffer<Integer>(10);
        for (int i = 0; i < 10; i++)
            bb.put(i);
        assertTrue(bb.isFull());
        assertFalse(bb.isEmpty());
    }
    /**
     * 测试阻塞行为以及对中断的响应性
     */
    @Test
    public void testTakeBlocksWhenEmpty() {
        final M_SemaphoreBoundedBuffer<Integer> bb = new M_SemaphoreBoundedBuffer<Integer>(10);
        Thread taker = new Thread() {
            public void run() {
                try {
                    int unused = bb.take();
                    fail(); // if we get here, it's an error
                } catch (InterruptedException success) {
                }
            }
        };
        try {
            taker.start();
            Thread.sleep(LOCKUP_DETECT_TIMEOUT);
            taker.interrupt();
            taker.join(LOCKUP_DETECT_TIMEOUT);
            assertFalse(taker.isAlive());
        } catch (Exception unexpected) {
            fail();
        }
    }

    /**
     * 测试资源泄漏
     */
    class Big {
        double[] data = new double[100000];
    }

    public void testLeak() throws InterruptedException {
        M_SemaphoreBoundedBuffer<Big> bb = new M_SemaphoreBoundedBuffer<Big>(CAPACITY);
        int heapSize1 = snapshotHeap();
        for (int i = 0; i < CAPACITY; i++)
            bb.put(new Big());
        for (int i = 0; i < CAPACITY; i++)
            bb.take();
        int heapSize2 = snapshotHeap();
        assertTrue(Math.abs(heapSize1 - heapSize2) < THRESHOLD);
    }

    private int snapshotHeap() {
        /* Snapshot heap and return heap size */
        return 0;
    }
}