package concurrent.A;

/**
 * 安全发布对象
 *
 *
 *没有使用同步确保C_Holder对其他线程可见,导致Holder未被正确发布
 * 此状态存在3情况
 * 1:除了发布对象的线程外,其他线程看到的holder域是一个失效值，因此将看到空引用or之前的旧值
 * 2:线程看到的holder引用的值是最新的,holder状态的值(n)是失效的(对象的构造函数会在子类构造函数运行之前将默认值写入所有的域)。
 * 3:某个线程在第一次读取域时得到失效值,而在次读取这个域时得到一个更新值,这也是assertSanity异常原因
 */
public class P_Holder {
    private int n;

    public P_Holder(int n) {
        this.n = n;
    }

    public void assertSanity() {
        if (n != n)
            throw new AssertionError("This statement is false.");
    }
}

/**
 * 没有足够同步情况下发布对象，会导致其他线程看到尚未创建完成的对象,调用assertSanity异常
 */
class StuffIntoPublic {
    public P_Holder holder;

    public void initialize() {
        holder = new P_Holder(42);
    }
}