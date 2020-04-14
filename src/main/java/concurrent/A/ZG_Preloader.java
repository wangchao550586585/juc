package concurrent.A;

import java.util.concurrent.*;

import static concurrent.LaunderThrowable.launderThrowable;

/**
 * Preloader
 *
 * Using FutureTask to preload data that is needed later
 * 使用FutureTask提前加载稍后需要的数据
 * @author Brian Goetz and Tim Peierls
 */
public class ZG_Preloader {
    /**
     * 加载生产信息
     * @return
     * @throws DataLoadException
     */
    ProductInfo loadProductInfo() throws DataLoadException {
        return null;
    }

    private final FutureTask<ProductInfo> future =
        new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
            public ProductInfo call() throws DataLoadException {
                return loadProductInfo();
            }
        });
    private final Thread thread = new Thread(future);

    public void start() { thread.start(); }

    public ProductInfo get()
            throws DataLoadException, InterruptedException {
        try {
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException)
                throw (DataLoadException) cause;
            else
                throw launderThrowable(cause);
        }
    }

    interface ProductInfo {
    }
}

class DataLoadException extends Exception { }
