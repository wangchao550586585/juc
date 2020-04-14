package concurrent.D.L;

import java.util.concurrent.atomic.*;


/**
 * AtomicPseudoRandom
 * <p/>
 * Random number generator using AtomicInteger
 * 线程安全
 * 基于AtomicInteger实现的随机数生成器
 * @author Brian Goetz and Tim Peierls
 */
public class AtomicPseudoRandom extends PseudoRandom {
    private AtomicInteger seed;

    AtomicPseudoRandom(int seed) {
        this.seed = new AtomicInteger(seed);
    }

    public int nextInt(int n) {
        while (true) {
            int s = seed.get();
            int nextSeed = calculateNext(s);
            if (seed.compareAndSet(s, nextSeed)) {
                int remainder = s % n;
                return remainder > 0 ? remainder : remainder + n;
            }
        }
    }
}
