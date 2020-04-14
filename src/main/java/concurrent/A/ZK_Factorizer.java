package concurrent.A;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

/**
 * 在因式分解使用Memoizer缓存结果
 *
 */
public class ZK_Factorizer extends GenericServlet implements Servlet {
    private final ZJ_Computable<BigInteger, BigInteger[]> c = arg -> factor(arg);
    private final ZJ_Computable<BigInteger, BigInteger[]> cache = new ZJ_Memoizer4<>(c);

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        try {
            BigInteger i = extractFromRequest(servletRequest);
            encodeIntoResponse(servletResponse, cache.compute(i));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    BigInteger[] factor(BigInteger i) {
        return new BigInteger[]{i};
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }
}
