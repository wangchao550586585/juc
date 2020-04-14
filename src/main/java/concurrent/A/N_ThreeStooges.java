package concurrent.A;

import java.util.*;

/**
 * 在可变对象基础上构建的不可变类
 */
public final class N_ThreeStooges {
    private final Set<String> stooges = new HashSet<String>();

    public N_ThreeStooges() {
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
    }

    public boolean isStooge(String name) {
        return stooges.contains(name);
    }

    public String getStoogeNames() {
        List<String> stooges = new Vector<String>();
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
        return stooges.toString();
    }
}
