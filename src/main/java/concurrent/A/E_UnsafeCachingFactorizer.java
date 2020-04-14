package concurrent.A;

import java.math.BigInteger;
import java.util.concurrent.atomic.*;
import javax.servlet.*;

/**
 * 线程不安全
 * 在没有足够原子性保证的情况下对其最近的计算结果缓存
 * 当在不变性条件中涉及多个变量时,各个变量之间并不是彼此独立的，而是某个变量的只会对其他变量的值产生约束。
 * 因此当更新某一个变量时，需要在同一个原子操作中对其他变量同时进行更新
 */
public class E_UnsafeCachingFactorizer extends GenericServlet implements Servlet {
    private final AtomicReference<BigInteger> lastNumber
            = new AtomicReference<BigInteger>();
    private final AtomicReference<BigInteger[]> lastFactors
            = new AtomicReference<BigInteger[]>();

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber.get()))
            encodeIntoResponse(resp, lastFactors.get());
        else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(resp, factors);
        }
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }
}

