package concurrent.A;

import java.util.*;

/**
 *监控车辆追踪器
 *
 *读取操作并发访问数据,因此模型需要线程安全。
 * 他所包含的map和可变的point都没有发布。需要返回车辆位置时，通过拷贝构造函数和deepCopy复制正确值，并返回新map。
 * 优点:保证一致性
 * 缺点:频繁复制，无法及时更新数据
 *
 返回的map只能保证容器不被修改,但是而不能保证容器内的可变对象不被修改，解决MutablePoint可变对象声明final
 */
public class T_MonitorVehicleTracker {
    private final Map<String, T_MutablePoint> locations;

    public T_MonitorVehicleTracker(Map<String, T_MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized Map<String, T_MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    public synchronized T_MutablePoint getLocation(String id) {
        T_MutablePoint loc = locations.get(id);
        return loc == null ? null : new T_MutablePoint(loc);
    }

    public synchronized void setLocation(String id, int x, int y) {
        T_MutablePoint loc = locations.get(id);
        if (loc == null)
            throw new IllegalArgumentException("No such ID: " + id);
        loc.x = x;
        loc.y = y;
    }

    private static Map<String, T_MutablePoint> deepCopy(Map<String, T_MutablePoint> m) {
        Map<String, T_MutablePoint> result = new HashMap<String, T_MutablePoint>();

        for (String id : m.keySet())
            result.put(id, new T_MutablePoint(m.get(id)));

        return Collections.unmodifiableMap(result);
    }
}

class T_MutablePoint {
    public int x, y;

    public T_MutablePoint() {
        x = 0;
        y = 0;
    }

    public T_MutablePoint(T_MutablePoint p) {
        this.x = p.x;
        this.y = p.y;
    }
}
