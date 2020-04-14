package concurrent.A;

/**
 * 可能出现情况
 * 1：一直循环,读线程永远看不到ready的值
 * 2：输出0,看到了ready值,没有看到之后写入number值,这种称之为"重排序",
 * 3：输出42
 * 解决办法：使用同步
 */
public class H_NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
