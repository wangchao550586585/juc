package concurrent.C.M;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;


/**
 * M_PutTakeTest
 * <p/>
 * Producer-consumer test program for BoundedBuffer
 * 测试M_SemaphoreBoundedBuffer的多生产者-多消费者程序
 * 测试并发类中的安全错误
 * 由于程序过于简单,可能直接串行处理，使用CyclicBarrier协调线程的启动和停止，从而来产生更多的并发交替操作。
 * @author Brian Goetz and Tim Peierls
 */
public class M_PutTakeTest {
    protected static final ExecutorService pool = Executors.newCachedThreadPool();
    protected CyclicBarrier barrier;
    protected final M_SemaphoreBoundedBuffer<Integer> bb;
    protected final int nTrials, nPairs;
    protected final AtomicInteger putSum = new AtomicInteger(0);
    protected final AtomicInteger takeSum = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        new M_PutTakeTest(10, 10, 100000).test(); // sample parameters
        pool.shutdown();
    }

    public M_PutTakeTest(int capacity, int npairs, int ntrials) {
        this.bb = new M_SemaphoreBoundedBuffer<Integer>(capacity);
        this.nTrials = ntrials;
        this.nPairs = npairs;
        this.barrier = new CyclicBarrier(npairs * 2 + 1);
    }

    void test() {
        try {
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
            barrier.await(); // wait for all threads to be ready
            barrier.await(); // wait for all threads to finish
            if(putSum.get()==takeSum.get()){
                System.out.println("=");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 随机数生成器
     * @param y
     * @return
     */
    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

    public class Producer implements Runnable {
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int) System.nanoTime());
                int sum = 0;
                barrier.await();
                for (int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public  class Consumer implements Runnable {
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
