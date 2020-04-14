package concurrent.D;

/**
 * PossibleReordering
 * <p/>
 * Insufficiently synchronized program that can have surprising results
 * 如果在程序中没有包含足够同步,可能产生奇怪结果
 * 同步将限制编译器,运行时和硬件对内存操作重排序的方式,从而使实施重排时不会破坏JMM提供的可见性保证
 * 线程不安全
 * @author Brian Goetz and Tim Peierls
 */
public class N_PossibleReordering {
    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            public void run() {
                a = 1;
                x = b;
            }
        });
        Thread other = new Thread(new Runnable() {
            public void run() {
                b = 1;
                y = a;
            }
        });
        one.start();
        other.start();
        one.join();
        other.join();
        System.out.println("( " + x + "," + y + ")");
    }
}
