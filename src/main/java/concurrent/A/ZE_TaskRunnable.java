package concurrent.A;

import java.util.concurrent.*;

/**
 * 阻塞方法与中断方法
 *  恢复中断状态以避免屏蔽中断
 *
 */
public class ZE_TaskRunnable implements Runnable {
    BlockingQueue<Task> queue;

    public void run() {
        try {
            processTask(queue.take());
        } catch (InterruptedException e) {
            // restore interrupted status
            //恢复被中断的状态
            Thread.currentThread().interrupt();
        }
    }

    void processTask(Task task) {
        // Handle the task
    }

    interface Task {
    }
}
