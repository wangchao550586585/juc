package concurrent.A;

import java.util.*;


/**
 * 通过封闭机制来确保线程安全
 * 对象可以封装在类的的一个实例中(私有成员),封装在某个作用域中(作为局部变量),封闭在线程内
 * 当一个对象被封装在另一个对象中,能够访问的代码都是已知的,利于代码分析。
 * 封闭机制与加锁结合起来,可以确保线程以安全的方式使用非线程安全的对象
 * <p>
 * 如果person是可变的,访问R_PersonSet获得mySet时还需要额外的同步。
 */
public class R_PersonSet {
    private final Set<Person> mySet = new HashSet<Person>();

    public synchronized void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person p) {
        return mySet.contains(p);
    }

    interface Person {
    }
}
