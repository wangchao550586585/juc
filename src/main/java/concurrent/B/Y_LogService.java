package concurrent.B;


import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * jvm关闭
 * 正常关闭中,jvm首先调用所有已注册的关闭钩子
 * 关闭钩子:通过Runtime.addShutdownHook注册但尚未开始的线程
 * jvm不能保证钩子的调用顺序,钩子没执行完,强行关闭。
 * 由于关闭钩子将并发执行,因此关闭日志文件时可能导致其他需要日志服务的关闭钩子产生问题
 * 解决:关闭钩子不应该依赖那些可能被应用程序或其他关闭钩子关闭的服务,对所有服务使用同一种钩子确保关闭操作在单个线程中串行执行
 */
public class Y_LogService {

    private final ExecutorService exec = null;
    private final Integer TIMEOUT = 10;
    private final TimeUnit unit = null;
    private final PrintWriter writer = null;

    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Y_LogService.this.stop();
            } catch (InterruptedException e) {
            }
        }));
    }

    public void stop() throws InterruptedException {
        try {
            exec.shutdown();
            exec.awaitTermination(TIMEOUT, unit);
        } finally {
            writer.close();
        }
    }

    public void log(String msg) {
        exec.execute(new Y_LogService.WriteTask(msg));
    }

    private class WriteTask implements Runnable {
        private String msg;

        public WriteTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            writer.write(msg);
        }
    }
}


