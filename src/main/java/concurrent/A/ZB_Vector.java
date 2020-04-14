package concurrent.A;

import java.util.Vector;

/**
 * 同步容器问题
 * 有些复合操作需要客户端加锁
 *
 * 譬如获取最后一个元素时,有一个线程删除了最后一个,抛出异常与其规范一致
 */
public class ZB_Vector {
    /**
     * 获取最后一个元素
     * @param list
     * @return
     */
    public static Object getLast(Vector list){
        synchronized (list){
            int lastIndex= list.size() - 1;
            return list.get(lastIndex);
        }
    }

    /**
     * 删除最后一个元素
     * @param list
     * @return
     */
    public static Object deleteLast(Vector list){
        synchronized (list){
            int lastIndex= list.size() - 1;
            return list.remove(lastIndex);
        }
    }

    /**
     * 迭代
     * @param list
     */
    public static void iterable(Vector list){
        synchronized (list){
            for (int i = 0; i < list.size(); i++) {
                 //doSomething(list.get(i));
            }
        }
    }
}
