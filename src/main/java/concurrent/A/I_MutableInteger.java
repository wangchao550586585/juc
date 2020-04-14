package concurrent.A;


/**
 * 非线程安全的类
 */
public class I_MutableInteger {
    private int value;

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }
}








