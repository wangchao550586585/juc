package concurrent.B;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用ExecutorService的日志服务
 * 关闭ExecutorService
 * @author WangChao
 * @create 2017/12/7 6:33
 */
public class S_LogService {
    private final ExecutorService exec = null;
    private final Integer TIMEOUT = 10;
    private final TimeUnit unit = null;
    private final PrintWriter writer=null;
    public void start() {

    }

    public void stop() throws InterruptedException {
        try {
            exec.shutdown();
            exec.awaitTermination(TIMEOUT,unit);
        } finally {
            writer.close();
        }
    }
    public void log(String msg){
        exec.execute(new WriteTask(msg));
    }

    private class WriteTask implements Runnable {
        private String msg;
        public WriteTask(String msg) {
            this.msg=msg;
        }

        @Override
        public void run() {
            writer.write(msg);
        }
    }
}
