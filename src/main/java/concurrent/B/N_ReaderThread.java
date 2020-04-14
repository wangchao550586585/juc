package concurrent.B;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * ReaderThread
 * <p/>
 * Encapsulating nonstandard cancellation in a Thread by overriding interrupt
 *改写interrupt()将非标准的取消操作封装在Thread中
 * 无论在read阻塞还是在某个可中断的阻塞方法中阻塞,都可以中断并停止当前任务
 * @author Brian Goetz and Tim Peierls
 */
public class N_ReaderThread extends Thread {
    private static final int BUFSZ = 512;
    private final Socket socket;
    private final InputStream in;

    public N_ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    public void interrupt() {
        try {
            //通过关闭底层输入流
            socket.close();
        } catch (IOException ignored) {
        } finally {
            //因为关闭流时下一步可能抛出异常，所以在finally调用父类
            super.interrupt();
        }
    }

    public void run() {
        try {
            byte[] buf = new byte[BUFSZ];
            while (true) {
                int count = in.read(buf);
                if (count < 0)
                    break;
                else if (count > 0)
                    processBuffer(buf, count);
            }
        } catch (IOException e) { /* Allow thread to exit */
        }
    }

    public void processBuffer(byte[] buf, int count) {
    }
}
