package concurrent.B;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 外部线程安排中断
 *调用线程中的任务,并安排一个取消任务，在运行指定时间中断。
 * 破坏了,在线程中断之前，应该了解他的中断策略,因为timedRun可以被任何线程调用
 * 如果任务在超时之前完成,中断timedRun之后执行,会出现问题，当然可以用ScheduledFuture来取消,但复杂
 * 不响应中断,timedRun会在任务结束时才执行,可能任务执行时间超过了时限。
 * @author WangChao
 * @create 2017/12/6 20:21
 */
public class K_TimedRun1 {
    private static final ScheduledExecutorService cancelExe= Executors.newScheduledThreadPool(1);
    public static void timedRun(Runnable r, long timeout, TimeUnit unit){
        final Thread thread = Thread.currentThread();
        cancelExe.schedule(() -> thread.interrupt(),timeout,unit);
        r.run();
    }
}
