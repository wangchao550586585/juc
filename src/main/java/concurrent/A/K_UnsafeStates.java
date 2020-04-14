package concurrent.A;

/**
 * 发布私有数组
 * 逸出:因为任何调用者都能修改数组内容,数组已经逸出了它所在的作用域
 */
public class K_UnsafeStates {
    private String[] states = new String[]{
        "AK", "AL" /*...*/
    };

    public String[] getStates() {
        return states;
    }
}
