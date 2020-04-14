package concurrent.B;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

/**
 * SwingUtilities
 * <p/>
 * Implementing SwingUtilities using an Executor
 * 使用Executor实现SwingUtilities
 * @author Brian Goetz and Tim Peierls
 */
public class ZF_SwingUtilities {
    private static final ExecutorService exec =
            Executors.newSingleThreadExecutor(new SwingThreadFactory());
    private static volatile Thread swingThread;

    private static class SwingThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            swingThread = new Thread(r);
            return swingThread;
        }
    }

    /**
     * 当前线程是否是事件线程
     * @return
     */
    public static boolean isEventDispatchThread() {
        return Thread.currentThread() == swingThread;
    }

    /**
     * 将Runnable任务调度到事件线程中执行(任意线程调用)
     * @param task
     */
    public static void invokeLater(Runnable task) {
        exec.execute(task);
    }

    /**
     * 将Runnable任务调度到事件线程中执行,并阻塞当前线程直到任务完成(非GUI线程中调用)
     * @param task
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    public static void invokeAndWait(Runnable task)
            throws InterruptedException, InvocationTargetException {
        Future f = exec.submit(task);
        try {
            f.get();
        } catch (ExecutionException e) {
            throw new InvocationTargetException(e);
        }
    }
}
