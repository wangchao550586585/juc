package concurrent.A;

import java.util.*;

/**
 * 辅助类扩展
 * 非线程安全的，没有则添加
 *
 * 因为list使用的锁不是BadListHelper上的锁,意味着putIfAbsent相对于list的其他操作不是原子的。
 * 解决:使List在实现客户端加锁和外部加锁使用同一把锁
 * 客户端加锁:对于使用某个对象X的客户端代码,使用X本身用于保护其状态的锁来保护这段客户代码
 *
 * 缺点：将类C的加锁代码放到与C完全无关的其他类中。
 * @param <E>
 */
class BadListHelper <E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);
        if (absent)
            list.add(x);
        return absent;
    }
}

/**
 * ThreadSafe
 * 通过客户端加锁来实现,若么有则添加
 * @param <E>
 */
class GoodListHelper <E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent)
                list.add(x);
            return absent;
        }
    }
}
