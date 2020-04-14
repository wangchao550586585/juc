package concurrent.A;

import java.math.BigInteger;
import javax.servlet.*;


/**
 * cache相关操作不会相互干扰,因为O_OneValueCache是不可变的,且在每条相应的代码路径中只会访问一次
 *volatile保证可见性
 * 使用指向不可变容器对象的volatile类型引用来缓存最新的结果
 * 通过使用包含多个状态变量的容器对象来维持不变性条件，并使用volatile的引用来确保可见性，
 * 使得本类没有显式使用锁任然是线程安全的
 */
public class O_VolatileCachedFactorizer extends GenericServlet implements Servlet {
    private volatile O_OneValueCache cache = new O_OneValueCache(null, null);

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = cache.getFactors(i);
        if (factors == null) {
            factors = factor(i);
            cache = new O_OneValueCache(i, factors);
        }
        encodeIntoResponse(resp, factors);
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        return new BigInteger[]{i};
    }
}

