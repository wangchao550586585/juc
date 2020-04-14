package concurrent.C;

import java.util.Random;

/**
 * 使用yield产生更多的交替操作,提高发现错误几率
 */
public class O_Test {
    private static final int THRESHOLD = 10000;

    public synchronized void transferCredits(Account from, Account to, int amount) {
        from.setBalance(from.getBalance() - amount);
        if (new Random().nextInt(1000) > THRESHOLD) Thread.yield();
        to.setBalance(to.getBalance() + amount);
    }

    class Account {

        private int balance;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
    }
}
