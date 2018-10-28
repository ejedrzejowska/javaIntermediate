package pl.sda.intermediate.threads;

import org.junit.jupiter.api.Test;

public class ThreadsExamples {
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
