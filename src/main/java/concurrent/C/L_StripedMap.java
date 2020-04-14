package concurrent.C;


/**
 * StripedMap
 * <p/>
 * Hash-based map using lock striping
 * 基于散列的Map种使用分段锁
 * 线程安全
 * @author Brian Goetz and Tim Peierls
 */
public class L_StripedMap {
    // Synchronization policy: buckets[n] guarded by locks[n%N_LOCKS]
    //可支持16个并发写入
    private static final int N_LOCKS = 16;
    private final Node[] buckets;
    private final Object[] locks;

    private static class Node {
        Node next;
        Object key;
        Object value;
    }

    public L_StripedMap(int numBuckets) {
        buckets = new Node[numBuckets];
        locks = new Object[N_LOCKS];
        for (int i = 0; i < N_LOCKS; i++)
            locks[i] = new Object();
    }

    private final int hash(Object key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    public Object get(Object key) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            for (Node m = buckets[hash]; m != null; m = m.next)
                if (m.key.equals(key))
                    return m.value;
        }
        return null;
    }

    /**
     * 这种操作不是原子操作,L_StripedMap为空时其他线程可能添加数据
     * 变成原子需要获得所有锁,但是像size/isEmpty结果可能无效
     */
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            synchronized (locks[i % N_LOCKS]) {
                buckets[i] = null;
            }
        }
    }
}
