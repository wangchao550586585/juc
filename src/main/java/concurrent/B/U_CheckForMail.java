package concurrent.B;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author WangChao
 * 校验邮件
 * 某个方法处理一批任务,当所有任务处理后才返回
 * 使用私有Executor,并且该Executor生命周期受限于方法调用
 * @create 2017/12/7 7:31
 */
public class U_CheckForMail {
    boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        //这里使用AtomicBoolean替代volatile类型的boolean,
        // 是因为能从内部的runnable中访问hasMail标志,因此他必须是final避免被修改
        final AtomicBoolean hasNewMail = new AtomicBoolean(false);
        try {
            for (final String host : hosts) {
                exec.execute(() -> {
                    if (checkMail(host)) {
                        hasNewMail.set(true);
                    }
                });
            }
        } finally {
            exec.shutdown();
            exec.awaitTermination(timeout, unit);
        }
        return hasNewMail.get();
    }

    private boolean checkMail(String host) {
        return false;
    }
}
