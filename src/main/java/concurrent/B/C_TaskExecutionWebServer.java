package concurrent.B;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * TaskExecutionWebServer
 * <p/>
 * Web server using a thread pool
 *  基于线程池的web服务器
 * @author Brian Goetz and Tim Peierls
 */
public class C_TaskExecutionWebServer {
    private static final int NTHREADS = 100;
    /**
     * 将请求处理任务的提交和任务的实际执行解耦,并且只需要采用不同的Executoor实现就可以改变服务器行为
     *
     */
    private static final Executor exec
            = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                public void run() {
                    handleRequest(connection);
                }
            };
            exec.execute(task);
        }
    }

    private static void handleRequest(Socket connection) {
        // request-handling logic here
    }
}
/**
 * 为所有任务启动新线程的Executor
 */
class ThreadPerTaskExecutor implements Executor{

    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}

/**
 * 同步方式执行所有任务的Executor
 */
class WithinThreadExecutor implements Executor{
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}