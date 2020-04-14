package concurrent.A;

import java.util.*;
import java.util.concurrent.*;

/**
 * 委托车辆追踪器
 *
 * 线程安全的委托
 *
 * Point不可变,所以他是线程安全,可以被自由共享与发布,因此返回时不需要复制
 * map访问都由ConcurrentHashMap管理，而且map所有键值不可变
 *返回一个不可修改却实时的车辆位置视图
 * A获取数据,B修改了数据,导致A获取数据实时更新,与之前获取数据不一致
 *优点:实时更新数据
 *缺点:导致不一样的车辆位置
 */
public class U_DelegatingVehicleTracker { 
    private final ConcurrentMap<String, U_Point> locations;
    private final Map<String, U_Point> unmodifiableMap;

    public U_DelegatingVehicleTracker(Map<String, U_Point> points) {
        locations = new ConcurrentHashMap<String, U_Point>(points);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    public Map<String, U_Point> getLocations() {
        return unmodifiableMap;
    }

    public U_Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new U_Point(x, y)) == null)
            throw new IllegalArgumentException("invalid vehicle name: " + id);
    }

    /**
     * 返回静态拷贝,而非实时拷贝
     * @return
     */
    // Alternate version of getLocations (Listing 4.8)
    public Map<String, U_Point> getLocationsAsStatic() {
        return Collections.unmodifiableMap(
                new HashMap<String, U_Point>(locations));
    }
}

 class U_Point {
    public final int x, y;

    public U_Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
