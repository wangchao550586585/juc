package concurrent.A;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap代替HashMap
 * 去掉Sync
 * 具备更好并发
 * 缺点:当多个线程执行compute,可能导致算出相同值
 * 特别是当执行一个开销很大的计算,其他线程不知道,可能会重复计算
 *
 * 解决办法:当X线程执行f(1),另一个执行f(1)的线程等待X计算结束,在去查缓存
 * @param <A>
 * @param <V>
 */
public class ZJ_Memoizer2 <A, V> implements ZJ_Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final ZJ_Computable<A, V> c;

    public ZJ_Memoizer2(ZJ_Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public  V compute(A arg) throws InterruptedException {
        V v = cache.get(arg);
        if (v==null){
            v=c.compute(arg);
            cache.put(arg,v);
        }
        return v;
    }
}
