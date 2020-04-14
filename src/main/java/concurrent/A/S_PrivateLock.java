package concurrent.A;


/**
 * 通过私有锁保护状态
 * 监视器模式:把对象所有可变状态封装起来,并由对象自己的内置锁来保护(如,Vector/Hashtable)
 */
public class S_PrivateLock {
    private final Object myLock = new Object();
    Widget widget;

    void someMethod() {
        synchronized (myLock) {
            // Access or modify the state of widget
        }
    }
}
