package concurrent.A;

import java.util.*;


/**
 * HiddenIterator
 * <p/>
 * Iteration hidden within string concatenation
 *  隐藏在toString中的迭代操作
 *  封装对象同步机制有利于确保实施同步策略。
 *  containsAll/removeAll/retainAll等也会迭代
 * @author Brian Goetz and Tim Peierls
 */
public class ZC_HiddenIterator {
    private final Set<Integer> set = new HashSet<Integer>();

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public synchronized void remove(Integer i) {
        set.remove(i);
    }

    public void addTenThings() {
        Random r = new Random();
        for (int i = 0; i < 10; i++)
            add(r.nextInt());
        //toString()会迭代元素
        System.out.println("DEBUG: added ten elements to " + set);
    }
}
