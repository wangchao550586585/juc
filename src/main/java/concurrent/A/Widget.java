package concurrent.A;

/**
 * 内置锁不是可重入的,产生死锁
 * 重入:某个线程试图获得一个已经由它自己持有的锁,就会成功
 * @author WangChao
 * @create 2017/11/22 7:25
 */
public class Widget {
    public synchronized void doSomething(){

    }
}
class  LoggingWidget extends Widget{
    public synchronized void doSomething(){
        System.out.println(toString());
        super.doSomething();
    }
}

