package concurrent.A;

/**
 *
 * 使用私有构造和工厂方法，解决this逸出
 * 只有当构造函数返回时,this引用才应该从线程中逸出,
 * 构造函数可以将this引用保存到某个地方，只要其他线程不会再构造函数完成之前使用它,就可以避免逸出
 */
public class M_SafeListener {
    private final EventListener listener;

    private M_SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }

    public static M_SafeListener newInstance(EventSource source) {
        M_SafeListener safe = new M_SafeListener();
        source.registerListener(safe.listener);
        return safe;
    }

    void doSomething(Event e) {
    }


    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }
}

