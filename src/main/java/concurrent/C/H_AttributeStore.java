package concurrent.C;

import java.util.*;
import java.util.regex.*;


/**
 * AttributeStore
 * <p/>
 * Holding a lock longer than necessary
 *  将一个锁不必要持有过长时间
 *  线程安全
 * @author Brian Goetz and Tim Peierls
 */
public class H_AttributeStore {
    private final Map<String, String>
            attributes = new HashMap<String, String>();

    public synchronized boolean userLocationMatches(String name,
                                                    String regexp) {
        String key = "users." + name + ".location";
        //这里访问了共享状态,只有这里才需要加锁
        String location = attributes.get(key);
        if (location == null)
            return false;
        else
            return Pattern.matches(regexp, location);
    }
}
