package concurrent.B;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Q_LogWriter
 * 通过一种不可靠的方式为日志服务增加支持
 */
public class Q_LogWriter {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private static final int CAPACITY = 1000;
    private volatile boolean shutdownRequest = false;

    public Q_LogWriter(Writer writer) {
        this.queue = new LinkedBlockingQueue<String>(CAPACITY);
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void cancel() {
        shutdownRequest = true;
    }

    public void log(String msg) throws InterruptedException {
        if (!shutdownRequest)
            queue.put(msg);
        else {
            throw new IllegalStateException("logger is shut down");
        }
    }

    private class LoggerThread extends Thread {
        private final PrintWriter writer;

        public LoggerThread(Writer writer) {
            this.writer = new PrintWriter(writer, true); // autoflush
        }

        public void run() {
            try {
                while (true)
                    writer.println(queue.take());
            } catch (InterruptedException ignored) {
            } finally {
                writer.close();
            }
        }
    }
}
