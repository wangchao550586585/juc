package concurrent.A;


/**
 * 线程不安全
 * 延迟初始化的竞态条件
 */
public class C_LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null)
            instance = new ExpensiveObject();
        return instance;
    }
}

class ExpensiveObject { }

