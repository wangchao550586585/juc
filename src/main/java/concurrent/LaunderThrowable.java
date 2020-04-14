package concurrent;

/**
 * @author WangChao
 * @create 2017/12/5 7:00
 */
public class LaunderThrowable {
    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException)
            return (RuntimeException) t;
        else if (t instanceof Error)
            throw (Error) t;
        else
            throw new IllegalStateException("Not unchecked", t);
    }
}
