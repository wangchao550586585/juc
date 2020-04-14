package concurrent.A;

import java.util.Map;
import java.util.concurrent.*;

import static concurrent.LaunderThrowable.launderThrowable;

/**
 * 基于FutureTask的Memoizer封装器
 * 结果计算出来,立即返回
 * 结果没出来,等待。
 * 依然存在2个线程计算相同值漏洞比ZJ_Memoizer2小
 * 在f(1)放入缓存时,另一个也执行相同的操作,则2个线程会执行2次
 * 原因:存在复合操作,没有则添加
 *
 * @param <A>
 * @param <V>
 */
public class ZJ_Memoizer3<A, V> implements ZJ_Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final ZJ_Computable<A, V> c;

    public ZJ_Memoizer3(ZJ_Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(final A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            FutureTask<V> ft = new FutureTask<>(() -> c.compute(arg));
            f = ft;
            cache.put(arg, ft);
            ft.run();
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }
}
