package concurrent.A;

import java.math.BigInteger;
import java.util.*;


/**
 * 确保初始化过程的安全性,共享这些对象时无需同步
 * 如果是一个不可变对象,当线程获取了对该对象的引用后,不必担心另一个线程会修改对象的状态
 * 如果要更新这些变量,就可以创建一个新的容器对象,其他使用原有对象的线程依然会看到对象处于一致的状态
 */
public class O_OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public O_OneValueCache(BigInteger i,
                         BigInteger[] factors) {
        lastNumber = i;
        lastFactors = Arrays.copyOf(factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
        if (lastNumber == null || !lastNumber.equals(i))
            return null;
        else
            //这里copy保证是不可变的
            return Arrays.copyOf(lastFactors, lastFactors.length);
    }
}
