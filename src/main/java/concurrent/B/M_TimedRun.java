package concurrent.B;

import java.util.concurrent.*;

import static concurrent.LaunderThrowable.launderThrowable;

/**
 * 通过future取消任务
 *
 * @author WangChao
 * @create 2017/12/6 21:13
 */
public class M_TimedRun {
    private static final ScheduledExecutorService cancelExe = Executors.newScheduledThreadPool(10);

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> submit = cancelExe.submit(r);
        try {
            submit.get(timeout, unit);
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        } catch (TimeoutException e) {
            //接下来任务将被取消
        } finally {
            //如果任务已经结束, 执行取消操作不会带来任何影响
            submit.cancel(true);//如果任务在运行将被中断
        }
    }
}
