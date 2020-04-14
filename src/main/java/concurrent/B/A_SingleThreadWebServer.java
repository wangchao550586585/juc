package concurrent.B;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A_SingleThreadWebServer
 * <p/>
 * Sequential web server
 *串行的web服务器
 *吞吐低
 * @author Brian Goetz and Tim Peierls
 */
public class A_SingleThreadWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            Socket connection = socket.accept();
            handleRequest(connection);
        }
    }

    private static void handleRequest(Socket connection) {
        // request-handling logic here
    }
}
