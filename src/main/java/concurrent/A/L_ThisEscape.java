package concurrent.A;

/**
 *
 * this逸出this.doSomething
 * 当从对象的构造函数中发布对象,只是发布了一个尚未构造完成的对象
 * this在构造过程中逸出,为不正确构造
 *
 * 1：这个内部类的实例中包含了对L_ThisEscape的隐式使用
 * 2：在构造函数中启动一个线程,this都会被线程共享
 * 3：构造函数中调用一个可改写实例方法(非private/final),this也会逸出
 *
 */
public class L_ThisEscape {
    public L_ThisEscape(EventSource source) {
        source.registerListener(new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        });
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

