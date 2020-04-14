package concurrent.A;

import java.math.BigInteger;
import javax.servlet.*;

/**
 * 线程不安全
 * count++会进行"读取-修改-写入"操作，不是原子操作
 * 在多次调用中会返回相同的值
 * 出现问题
 *1:竞态条件:不恰当执行时序而出现不正确的结果
 *2:失效数据:(stale data)
 */
public class B_UnsafeCountingFactorizer extends GenericServlet implements Servlet {
    private long count = 0;

    public long getCount() {
        return count;
    }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        ++count;
        encodeIntoResponse(resp, factors);
    }

    void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}
