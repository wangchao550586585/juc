package concurrent.C.P;

import concurrent.C.M.M_PutTakeTest;

import java.util.concurrent.CyclicBarrier;

/**
 * TimedPutTakeTest
 * <p/>
 * Testing with a barrier-based timer
 *  采用基于栅栏的定时器进行测试
 * @author Brian Goetz and Tim Peierls
 */
public class TimedPutTakeTest extends M_PutTakeTest {
    private BarrierTimer timer = new BarrierTimer();

    public TimedPutTakeTest(int cap, int pairs, int trials) {
        super(cap, pairs, trials);
        //设置定时器
        barrier = new CyclicBarrier(nPairs * 2 + 1, timer);
    }

    public void test() {
        try {
            timer.clear();
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new M_PutTakeTest.Producer());
                pool.execute(new M_PutTakeTest.Consumer());
            }
            barrier.await();
            barrier.await();

            //获取每次操作的运行时间平均值
            long nsPerItem = timer.getTime() / (nPairs * (long) nTrials);
            System.out.print("Throughput: " + nsPerItem + " ns/item");

            if(putSum.get()==takeSum.get()){
                System.out.println("=");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        int tpt = 100000; // 每个线程中的测试次数
        for (int cap = 1; cap <= 1000; cap *= 10) {//信号量次数
            System.out.println("Capacity: " + cap);
            for (int pairs = 1; pairs <= 128; pairs *= 2) { //生产者消费者数量
                TimedPutTakeTest t = new TimedPutTakeTest(cap, pairs, tpt);
                System.out.print("Pairs: " + pairs + "\t");
                t.test();
                System.out.print("\t");
                Thread.sleep(1000);
                t.test();
                System.out.println();
                Thread.sleep(1000);
            }
        }
        M_PutTakeTest.pool.shutdown();
    }
}
