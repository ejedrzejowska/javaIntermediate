package pl.sda.intermediate.threads.bank;

import java.util.Random;

public class ClientActionRunnable implements Runnable {
    @Override
    public void run() {
        Integer random = new Random().nextInt(101);
        Bank.withdraw(random);
        try {
            Thread.sleep(101);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Bank.depositBack(random);
    }
}
