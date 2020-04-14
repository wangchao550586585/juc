package concurrent.B.ZE;

import java.util.concurrent.atomic.*;

/**
 * PuzzleSolver
 * <p/>
 * Solver that recognizes when no solution exists
 * 并发解决器找不到答案,退出。
 * 记录活动任务数量,该值为0时,将解答设置为null,
 * @author Brian Goetz and Tim Peierls
 */
public class PuzzleSolver <P,M> extends ConcurrentPuzzleSolver<P, M> {
    PuzzleSolver(Puzzle<P, M> puzzle) {
        super(puzzle);
    }

    private final AtomicInteger taskCount = new AtomicInteger(0);

    protected Runnable newTask(P p, M m, PuzzleNode<P, M> n) {
        return new CountingSolverTask(p, m, n);
    }

    class CountingSolverTask extends SolverTask {
        CountingSolverTask(P pos, M move, PuzzleNode<P, M> prev) {
            super(pos, move, prev);
            taskCount.incrementAndGet();
        }

        public void run() {
            try {
                super.run();
            } finally {
                if (taskCount.decrementAndGet() == 0)
                    solution.setValue(null);
            }
        }
    }
}
