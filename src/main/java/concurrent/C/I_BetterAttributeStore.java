package concurrent.C;

import java.util.*;
import java.util.regex.*;

/**
 * BetterAttributeStore
 * <p/>
 * Reducing lock duration
 *  减少锁持有时间
 *
 * @author Brian Goetz and Tim Peierls
 */
public class I_BetterAttributeStore {
    private final Map<String, String>
            attributes = new HashMap<String, String>();

    public boolean userLocationMatches(String name, String regexp) {
        //实例化StringBuilder对象
        String key = "users." + name + ".location";
        String location;
        //访问共享状态处加锁
        synchronized (this) {
            location = attributes.get(key);
        }
        if (location == null)
            return false;
        else
            return Pattern.matches(regexp, location);
    }
}
