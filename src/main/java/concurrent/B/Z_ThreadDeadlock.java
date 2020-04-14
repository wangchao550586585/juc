package concurrent.B;

import java.util.concurrent.*;

/**
 * ThreadDeadlock
 * <p/>
 * Task that deadlocks in a single-threaded Executor
 * 在单线程Executor中任务发生死锁
 * 线程饥饿死锁：所有正在执行任务的线程由于等待其他依然处于工作队列中的任务而阻塞
 * 如下:单线程的Exector中,一个任务等待另外2个提交给相同的Exector任务。另外2个任务也在等待，会造成饥饿死锁
 * 解决办法：预先设计好线程池大小
 * @author Brian Goetz and Tim Peierls
 */
public class Z_ThreadDeadlock {
    ExecutorService exec = Executors.newSingleThreadExecutor();

    public class LoadFileTask implements Callable<String> {
        private final String fileName;

        public LoadFileTask(String fileName) {
            this.fileName = fileName;
        }

        public String call() throws Exception {
            // Here's where we would actually read the file
            return "";
        }
    }

    public class RenderPageTask implements Callable<String> {
        public String call() throws Exception {
            Future<String> header, footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            // Will deadlock -- task waiting for result of subtask
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            // Here's where we would actually render the page
            return "";
        }
    }
}
