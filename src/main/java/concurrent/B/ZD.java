package concurrent.B;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class ZD {
    /**
     * 串行更改并行
     */
    private void process(Object object) {
    }

    void processSequentially(List<Object> objects) {
        objects.forEach(object -> process(object));
    }

    void processInParallel(Executor executor, List<Object> objects) {
        objects.forEach(object -> executor.execute(() -> process(object)));
    }

    /**
     * 串行递归转换为并行递归
     */
    public <T> void sequentialRecursive(List<Node<T>> nodes, Collection<T> result) {
        nodes.forEach(node -> {
            result.add(node.compute());
            sequentialRecursive(node.getChildren(), result);
        });
    }

    public <T> void parallelRecursive(final Executor executor, List<Node<T>> nodes, Collection<T> result) {
        nodes.forEach(node -> {
            executor.execute(() -> result.add(node.compute()));
            sequentialRecursive(node.getChildren(), result);
        });
    }

    /**
     * 等待通过并行方式计算的结果
     * @param nodes
     * @param <T>
     * @return
     * @throws InterruptedException
     */
    public <T> Collection<T> getParallelResults(List<Node<T>> nodes) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ConcurrentLinkedQueue<T> ts = new ConcurrentLinkedQueue<>();
        parallelRecursive(executorService, nodes, ts);
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return ts;
    }

    private class Node<T> {
        private List<Node<T>> children;

        public T compute() {
            return null;
        }

        public List<Node<T>> getChildren() {
            return children;
        }
    }
}
