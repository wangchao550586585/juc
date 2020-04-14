package concurrent.B;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;


/**
 * PrimeGenerator
 * <p/>
 * Using a volatile field to hold cancellation state
 * 使用volatile保存取消状态
 * 取消策略:通过调用cancel请求取消,每次搜索素数时先检查是否存在取消请求，存在则退出
 *线程安全
 * @author Brian Goetz and Tim Peierls
 */
public class G_PrimeGenerator implements Runnable {
    private static ExecutorService exec = Executors.newCachedThreadPool();

    private final List<BigInteger> primes
            = new ArrayList<BigInteger>();
    private volatile boolean cancelled;

    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancelled) {
            p = p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
            }
        }
    }

    public void cancel() {
        cancelled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<BigInteger>(primes);
    }

    static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        G_PrimeGenerator generator = new G_PrimeGenerator();
        exec.execute(generator);
        try {
            SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }
        return generator.get();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(aSecondOfPrimes());
    }
}
