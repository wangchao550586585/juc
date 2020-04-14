package concurrent.C;

import java.util.concurrent.*;

/**
 * WorkerThread
 * <p/>
 * Serialized access to a task queue
 *
 * @author Brian Goetz and Tim Peierls
 */

public class G_WorkerThread extends Thread {
    private final BlockingQueue<Runnable> queue;

    public G_WorkerThread(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                //串行,所有工作线程共享同一个工作队列，要保证并发访问维持队列完整
                //如果通过加锁保护队列状态,取出任务将串行化
                Runnable task = queue.take();
                task.run();
            } catch (InterruptedException e) {
                break; /* Allow thread to exit */
            }
        }
    }
}
