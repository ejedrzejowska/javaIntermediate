package pl.sda.intermediate.threads.bank;

import java.util.concurrent.atomic.AtomicInteger;

public class Bank {
    private static int counter;
    private static Integer balance = 10000;

    private static AtomicInteger atomicBalance = new AtomicInteger(10000);


    public static int getCounter() {
        return counter;
    }

    public static Integer getBalance() {
        return balance;
    }

    public static /*synchronized*/ void withdraw(Integer howMuch){
        balance -= howMuch; //nie zadziała poprawnie, ponieważ operacje zachodzą współbieżnie, zadziała z 'synchronized'
        atomicBalance.getAndUpdate(x -> x - howMuch);
        System.out.println(Thread.currentThread().getName() + " stan konta po wypłacie: " + balance);
    }

    public static /*synchronized*/ void depositBack(Integer howMuch){
        balance += howMuch;
        atomicBalance.getAndUpdate(x -> x + howMuch);
        System.out.println(Thread.currentThread().getName() + " stan konta po wpłacie: " + balance);
        counter++;
    }

    public static AtomicInteger getAtomicBalance() {
        return atomicBalance;
    }

}
