package concurrent.B;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static concurrent.LaunderThrowable.launderThrowable;

/**
 * 在专门线程中中断任务
 * 拥有自己的执行策略,即便任务不响应中断,限时运行的方法依然能返回到他的调用者。
 * 由于他依赖join,所以无法得知执行控制是因为线程正常退出还是因为join超时而退出
 * @author WangChao
 * @create 2017/12/6 20:34
 */
public class L_TimedRun2 {
    private static final ScheduledExecutorService cancelExe= Executors.newScheduledThreadPool(10);
    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;
            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable e) {
                    this.t = e;
                }
            }
            void rethrow() {
                if (t != null) {
                    throw launderThrowable(t);
                }
            }
        }
        RethrowableTask task = new RethrowableTask();
            final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExe.schedule(() -> taskThread.interrupt(),timeout,unit);
        //把指定的线程加入到当前线程,等待 taskThread 线程，等待时间是timeout秒
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }

}
