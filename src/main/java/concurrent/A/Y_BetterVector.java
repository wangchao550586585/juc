package concurrent.A;

import java.util.*;

/**
 * Extending Vector to have a put-if-absent method
 * 扩展Vector,没有则添加。
 * 缺点：将类的加锁代码分布多个类中
 * 扩展类方法策略
 * 1：将扩展方法添加源类,
 * 2：extend
 * 3：辅助类
 */
public class Y_BetterVector <E> extends Vector<E> {
    // When extending a serializable class, you should redefine serialVersionUID
    static final long serialVersionUID = -3963416950630760754L;

    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains(x);
        if (absent)
            add(x);
        return absent;
    }
}
