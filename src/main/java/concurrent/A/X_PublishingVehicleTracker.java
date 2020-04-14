package concurrent.A;

import java.util.*;
import java.util.concurrent.*;

/**
 * 安全发布底层状态的车辆追踪器
 * 将线程安全性委托给底层ConcurrentHashMap,map中的元素是线程安全的且可变的point。
 * getLocations返回底层map对象的一个不可变副本，调用者不能增加或删除车辆,却可以修改map中的point改变车辆位置
 * 如果在车辆位置有效值上添加任何约束,就需要添加锁。
 */
public class X_PublishingVehicleTracker {
    private final Map<String, X_SafePoint> locations;
    private final Map<String, X_SafePoint> unmodifiableMap;

    public X_PublishingVehicleTracker(Map<String, X_SafePoint> locations) {
        this.locations = new ConcurrentHashMap<String, X_SafePoint>(locations);
        this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
    }

    public Map<String, X_SafePoint> getLocations() {
        return unmodifiableMap;
    }

    public X_SafePoint getLocation(String id) {
        return locations.get(id);
    }

    /**
     * 在不在map里面，生成构造时就确定了。所以如果没有也没法添加。
     * @param id
     * @param x
     * @param y
     */
    public void setLocation(String id, int x, int y) {
        if (!locations.containsKey(id))
            throw new IllegalArgumentException("invalid vehicle name: " + id);
        locations.get(id).set(x, y);
    }
}
class X_SafePoint {
    private int x, y;

    /**
     * 为什么接受数组为参数的构造器不能公开，数组a是有外部传入的，并不能保证数组内容不会其他线程修改。
     * @param a
     */
    private X_SafePoint(int[] a) {
        this(a[0], a[1]);
    }

    /**
     * 如果调用的是this(p.x,p.y)会产生竞态条件,而私有构造函数则可以避免。私有构造函数捕获模式
     *
     * @param p
     */
    public X_SafePoint(X_SafePoint p) {
        this(p.get());
    }


    /**
     * 常量修改并不会影响传递的值
     * @param x
     * @param y
     */
    public X_SafePoint(int x, int y) {
        this.set(x, y);
    }

    /**
     * get()方法是用synchronized修饰的，锁在对象上的，保证了对象状态的一致性
     * @return
     */
    public synchronized int[] get() {
        return new int[]{x, y};
    }

    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
}