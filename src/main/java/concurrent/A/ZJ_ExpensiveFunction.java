package concurrent.A;

import java.math.BigInteger;

public class  ZJ_ExpensiveFunction implements ZJ_Computable<String,BigInteger>{

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        //模拟计算长时间
        Thread.sleep(1000);
        return new BigInteger(arg);
    }
}