package concurrent.D;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * B_InterruptibleLocking
 * 可中断的锁获取操作
 * @author Brian Goetz and Tim Peierls
 */
public class B_InterruptibleLocking {
    private Lock lock = new ReentrantLock();

    public boolean trySendOnSharedLine(String message,
                                       long timeout, TimeUnit unit)
            throws InterruptedException {
        long nanosToLock = unit.toNanos(timeout)
                - estimatedNanosToSend(message);
        if (!lock.tryLock(nanosToLock, NANOSECONDS))
            return false;
        try {
            return sendOnSharedLine(message);
        } finally {
            lock.unlock();
        }
    }
    private boolean cancellableSendOnSharedLine(String message) throws InterruptedException {
        return true;
    }
    long estimatedNanosToSend(String message) {
        return message.length();
    }

    public boolean sendOnSharedLine(String message)
            throws InterruptedException {
        //显式中断点
        lock.lockInterruptibly();
        try {
            return cancellableSendOnSharedLine(message);
        } finally {
            lock.unlock();
        }
    }


}
