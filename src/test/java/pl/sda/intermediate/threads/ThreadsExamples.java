package pl.sda.intermediate.threads;

import org.junit.jupiter.api.Test;
import pl.sda.intermediate.threads.bank.Bank;
import pl.sda.intermediate.threads.bank.ClientActionRunnable;

import java.util.ArrayList;
import java.util.List;

public class ThreadsExamples {

    @Test
    void bankExampleSynchronized(){
        for (int i = 0; i < 1000; i++) {
            new ClientActionRunnable().run();
        }
    }

    @Test
    void bankExampleSeparateThreads(){
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threadList.add(new Thread(new ClientActionRunnable()));
        }
        for (Thread thread : threadList) {
            thread.start(); //nie run, bo run zapusci w watku main
        }
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("balans po: " + Bank.getBalance());
        System.out.println("Bilans atomowy na koniec: " + Bank.getAtomicBalance());
        System.out.println("Liczba operacji: " +  Bank.getCounter());
    }

    @Test
    public void runnableBasics(){
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName() + " Lambda runnable");
        Runnable runnableAnonymous = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " Anonymous lambda runnable");
            }
        };
        Runnable ourRunnable = new OurRunnable();
        runnable.run();
        runnableAnonymous.run();
        ourRunnable.run();
        Thread myThread = new Thread(runnable);
        Thread mySedondThread = new Thread(runnableAnonymous);
        Thread myThirdThread = new Thread(ourRunnable);
        myThread.start();
        mySedondThread.start();
        myThirdThread.start();
    }
    private class OurRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Our runnable");
        }
    }
}
