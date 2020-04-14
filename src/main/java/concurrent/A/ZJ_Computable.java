package concurrent.A;

public interface ZJ_Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}