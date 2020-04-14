package concurrent.B;

/**
 *处理非正常的线程终止
 * 任务抛出异常,它将使线程终结,但会首先通知框架线程已经终结
 * 确保行为糟糕任务不会影响后续任务执行
 * ThreadPoolExcutor和swing都实现了
 */
public class W implements Runnable {
    @Override
    public void run() {
        Throwable throwable = null;
        try {
            while (!isInterrupted()) {
                runTask(getTaskFromWorkQueue());
            }
        } catch (Throwable e) {
            throwable = e;
        } finally {
            threadExited(this, throwable);
        }

    }

    ///////////////////////////////////
    private void threadExited(W w, Throwable throwable) {
    }

    private void runTask(Object taskFromWorkQueue) {

    }

    private Object getTaskFromWorkQueue() {
        return null;
    }

    public boolean isInterrupted() {
        return false;
    }
}
