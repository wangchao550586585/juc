package concurrent.B;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.logging.*;

/**
 * LifecycleWebServer
 * <p/>
 * Web server with shutdown support
 * 支持关闭操作的web服务器
 * ExecutorService添加声明周期方法
 * 运行-关闭-已终止
 * shutdown:平缓关闭,不在接受新任务,等待已经提交的任务执行完成,包括还未开始执行的任务
 * shutdownNow:取消所有运行任务,不在启动队列中尚未开始执行的任务
 * awaitTermination:阻塞等待ExecutorService到达终止状态
 * isTerminated:轮询ExecutorService是否已经终止
 * @author Brian Goetz and Tim Peierls
 */
public class D_LifecycleWebServer {
    private final ExecutorService exec = Executors.newCachedThreadPool();

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (!exec.isShutdown()) {
            try {
                final Socket conn = socket.accept();
                exec.execute(new Runnable() {
                    public void run() {
                        handleRequest(conn);
                    }
                });
            } catch (RejectedExecutionException e) {
                if (!exec.isShutdown())
                    log("task submission rejected", e);
            }
        }
    }

    public void stop() {
        exec.shutdown();
    }

    private void log(String msg, Exception e) {
        Logger.getAnonymousLogger().log(Level.WARNING, msg, e);
    }

    void handleRequest(Socket connection) {
        Request req = readRequest(connection);
        if (isShutdownRequest(req))
            stop();
        else
            dispatchRequest(req);
    }

    interface Request {
    }

    private Request readRequest(Socket s) {
        return null;
    }

    private void dispatchRequest(Request r) {
    }

    private boolean isShutdownRequest(Request r) {
        return false;
    }
}
