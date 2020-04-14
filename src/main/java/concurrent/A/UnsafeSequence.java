package concurrent.A;


/**
 * 会出现：
 * 1:竞态条件
 * 2:失效数据(stale data)
 */
public class UnsafeSequence {
    private int value;

    /**
     * Returns a unique value.
     */
    public int getNext() {
        return value++;
    }
}
