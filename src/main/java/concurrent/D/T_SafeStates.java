package concurrent.D;

import java.util.HashMap;
import java.util.Map;

/**
 * SafeStates
 * <p/>
 * Initialization safety for immutable objects
 * 线程安全
 * final对象的初始化安全性
 * @author Brian Goetz and Tim Peierls
 */
public class T_SafeStates {
    private final Map<String, String> states;

    public T_SafeStates() {
        states = new HashMap<String, String>();
        states.put("alaska", "AK");
        states.put("alabama", "AL");
        /*...*/
        states.put("wyoming", "WY");
    }

    public String getAbbreviation(String s) {
        return states.get(s);
    }
}
