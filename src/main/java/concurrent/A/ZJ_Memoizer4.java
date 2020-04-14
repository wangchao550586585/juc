package concurrent.A;

import java.util.Map;
import java.util.concurrent.*;

import static concurrent.LaunderThrowable.launderThrowable;

/**
 * 最终实现
 * 使用putIfAbsent保证原子性
 * 当缓存的是Future,而不是值,将导致缓存污染,如果某个计算被取消或失败,那么计算这个结果时将指明计算过程被取消或者失败。
 * 为了避免这种情况,如果发现计算被取消,将Future从缓存中移除,如果检测RuntimeException也会移除,这样才能保证将来计算会成功
 * 还可以增加的功能:
 * 缓存逾期:通过FutureTask子类来解决,在子类中为每个结果指定一个逾期时间,并定期扫描缓存中逾期的元素
 * 缓存清理:移除旧的计算结果以便为新的计算结果腾出时间,从而使缓存不会消耗过多内存
 *
 * @param <A>
 * @param <V>
 */
public class ZJ_Memoizer4<A, V> implements ZJ_Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final ZJ_Computable<A, V> c;

    public ZJ_Memoizer4(ZJ_Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(final A arg) throws InterruptedException {
        //添加循环,保证运行时期异常依然会在次算出结果
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                FutureTask<V> ft = new FutureTask<>(() -> c.compute(arg));
                f = cache.putIfAbsent(arg, ft);
                //f==null,说明第一次添加成功
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {//运行时期异常
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                throw launderThrowable(e.getCause());
            }
        }
    }
}
