package concurrent.B;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.*;

/**
 * LogWriter
 * <p/>
 * Producer-consumer logging service with no shutdown support
 * 采用多生产者单消费者
 *不支持关闭的生产者-消费者日志服务
 * 解决方法:
 *      1：因为take能响应中断,将日志线程修改捕获InterruptedException时退出,中断日志线程就能退出
 *             直接关闭会丢失正在等待写入的日志信息,其他线程将在调用log时被阻塞,因为日志消息队列时满的,因此这些线程无法解除阻塞。
 *      2：取消一个生产者-消费者操作时,需要同时取消生产者和消费者,因为生产者不是专门的线程,取消非常困难
 *      3：设置某个"已请求关闭"标志,避免进一步提交日志。
 *              收到关闭请求后,消费者将队列中所有消息写入日志,并解除所有在调用log时阻塞的生产者
 *              因为这个方法存在竞态,log实现的是先判断在运行的代码序列,生产者发现该服务还没有关闭,
 *              因此在关闭服务后依然会将日志放入队列,会造成生产者调用log阻塞且无法解除阻塞
 *              可以通过宣布清空队列被清空前,让生产者等待数秒,也可能导致问题存在
 * @author Brian Goetz and Tim Peierls
 */
public class P_LogWriter {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private static final int CAPACITY = 1000;

    public P_LogWriter(Writer writer) {
        this.queue = new LinkedBlockingQueue<String>(CAPACITY);
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void log(String msg) throws InterruptedException {
        queue.put(msg);
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
