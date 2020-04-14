package concurrent.B;

import java.util.*;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * OutOfTime
 * <p/>
 * Class illustrating confusing Timer behavior
 *  抛出异常终止定时线程
 * @author Brian Goetz and Tim Peierls
 */
public class E_OutOfTime {
    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(1);
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(5);
    }

    static class ThrowTask extends TimerTask {
        public void run() {
            throw new RuntimeException();
        }
    }
}
