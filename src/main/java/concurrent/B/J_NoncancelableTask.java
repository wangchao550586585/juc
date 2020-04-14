package concurrent.B;

import java.util.concurrent.BlockingQueue;

/**
 * @author WangChao
 * 对于一些不支持取消但仍可以调用可中断阻塞方法的操作,他们必须在循环中调用这些方法,并在发现中断后尝试
 * 他们应该本地保存中断状态,并在返回前恢复。而不是捕获InterruptedException时恢复。否则可能会引起无限循环。
 * 因为大多数可中断的阻塞方法都会在入口检查中断状态，并且发现当该状态被设置时立刻抛出InterruptedException
 *
 * 不可取消的任务在退出前恢复中断
 * @create 2017/12/6 20:09
 */
public class J_NoncancelableTask {
    public Task getNextTash(BlockingQueue<Task> queue) {
        boolean interrupted = false;
        try {
            while (true) {
                try {
                    return queue.take();
                } catch (InterruptedException e) {
                    interrupted = true;
                    //重新尝试
                }
            }
        } finally {
            if (interrupted) Thread.currentThread().interrupt();
        }
    }
    interface Task {
    }
}
