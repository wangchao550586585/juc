package concurrent.B;

import java.math.BigInteger;
import java.util.concurrent.*;

/**
 * PrimeProducer
 * <p/>
 * Using interruption for cancellation
 * 使用中断而不是boolean标志来请求取消,
 * 在阻塞的put方法调用中,以及在循环开始查询中断状态时
 * @author Brian Goetz and Tim Peierls
 */
public class I_PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    I_PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted())
                queue.put(p = p.nextProbablePrime());
        } catch (InterruptedException consumed) {
            /* Allow thread to exit */
        }
    }

    public void cancel() {
        interrupt();
    }
}
