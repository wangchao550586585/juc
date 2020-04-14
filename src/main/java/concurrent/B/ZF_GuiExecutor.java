package concurrent.B;

import java.util.*;
import java.util.concurrent.*;

/**
 * GuiExecutor
 * <p/>
 * Executor built atop SwingUtilities
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ZF_GuiExecutor extends AbstractExecutorService {
    // Singletons have a private constructor and a public factory
    private static final ZF_GuiExecutor instance = new ZF_GuiExecutor();

    private ZF_GuiExecutor() {
    }

    public static ZF_GuiExecutor instance() {
        return instance;
    }

    public void execute(Runnable r) {
        if (ZF_SwingUtilities.isEventDispatchThread())
            r.run();
        else
            //也可以用Display.asyncExec
            ZF_SwingUtilities.invokeLater(r);
    }

    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }
}
