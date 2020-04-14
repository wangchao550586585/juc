package concurrent.A;

import java.math.BigInteger;
import javax.servlet.*;


/**
 * 修改为2个独立的同步块,
 */
public class G_CachedFactorizer extends GenericServlet implements Servlet {
    private BigInteger lastNumber;
    private BigInteger[] lastFactors;
    /*
    添加计数器,也可以用AtomicLong
    但是这里已经使用了同步代码块了,使用2种同步机制会造成混乱，且不会有任何性能提升
     */
    private long hits;
    private long cacheHits;

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = null;
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null) {
            factors = factor(i);
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
        encodeIntoResponse(resp, factors);
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
