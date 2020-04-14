package concurrent.A;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用HashMap和同步机制缓存
 * 每次只有一个线程执行compute
 * 如果多个线程在排队等待还未计算的结果,那么compute计算时间可能没有"记忆"操作的时间更长
 * @param <A>
 * @param <V>
 */
public class ZJ_Memoizer1<A, V> implements ZJ_Computable<A, V> {
    private f1inal Map<A, V> cache = new HashMap<A, V>();
    private final ZJ_Computable<A, V> c;

    public ZJ_Memoizer1(ZJ_Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V v = cache.get(arg);
        if (v==null){
            v=c.compute(arg);
            cache.put(arg,v);
        }
        return v;
    }
}
