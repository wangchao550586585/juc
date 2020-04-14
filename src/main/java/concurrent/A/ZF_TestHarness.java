package concurrent.A;

import java.util.concurrent.*;

/**
 * TestHarness
 * <p/>
 * Using CountDownLatch for starting and stopping threads in timing tests
 *使用CountDownLatch达到闭锁目的
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ZF_TestHarness {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(new ZF_TestHarness().timeTasks(5, () -> System.out.println(1)));
    }

    public long timeTasks(int nThreads, final Runnable task)
            throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        //等待计时器=0
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            //递减
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        //使主线程能释放所有工作线程
        startGate.countDown();
        //使主线程能够等待最后一个线程执行完成,而不是顺序等待每个线程完成
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }
}
