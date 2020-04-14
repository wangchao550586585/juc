package concurrent.B;

import java.util.concurrent.*;


/**
 * BoundedExecutor
 * <p/>
 * Using a Semaphore to throttle task submission
 * 线程安全,用Semaphore控制任务的提交速率
 * 当工作队列被填满后,没有预定于的饱和策略来阻塞executor
 * 通过semaphore显示任务的到达率实现
 * 使用一个无界队列(因为不能限制队列大小和任务到达率)
 * 设置信号量的上界为线程池大小+可排队任务的数量,因为信号量需要控制正在执行和等待执行的任务数量
 * @author Brian Goetz and Tim Peierls
 */
public class ZA_BoundedExecutor {
    private final Executor exec;
    private final Semaphore semaphore;

    public ZA_BoundedExecutor(Executor exec, int bound) {
        this.exec = exec;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command)
            throws InterruptedException {
        semaphore.acquire();
        try {
            exec.execute(() -> {
                try {
                    command.run();
                } finally {
                    semaphore.release();
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
        }
    }
}
