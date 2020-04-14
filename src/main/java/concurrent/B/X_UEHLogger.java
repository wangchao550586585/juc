package concurrent.B;

import java.util.logging.*;

/**
 * UEHLogger
 * <p/>
 * UncaughtExceptionHandler that logs the exception
 * 将异常写入日志的UncaughtExceptionHandler
 * UncaughtExceptionHandler:能检测出某个线程由于未捕获的异常而终结的情况
 * 当一个线程由于未捕获异常而退出,jvm会把事件报告给应用程序提供的UncaughtExceptionHandler异常处理器
 * 如果没有提供UncaughtExceptionHandler,默认将栈追踪信息输出到system.err
 *
 *
 * @author Brian Goetz and Tim Peierls
 */
public class X_UEHLogger implements Thread.UncaughtExceptionHandler {
    /**
     * 将错误信息写入程序日志
     * 也可以做一些重新启动线程,关闭应用程序等操作
     * @param t
     * @param e
     */
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "Thread terminated with exception: " + t.getName(), e);
    }

}
