package pl.sda.intermediate.threads;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

public class CompletableFutureExample {

    private Function<String, String> stringToString = u -> u;
    private Function<BigDecimal, String> bigDecimalToString = u -> u.toPlainString();
    private Function<Long, String> longToString = u -> u.toString();

    private <T> String transform(T value, Function<T, String> function){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return function.apply(value);
    }

    @Test
    public void oneByOne(){
        downloadDescription();
        downloadPhotos();
        downloadAdditionalData();
        downloadPrice();
    }

    @Test
    public void oneByOne2(){
        transform(downloadDescription(), stringToString);
        transform(downloadPhotos(), stringToString);
        transform(downloadAdditionalData(), longToString);
        transform(downloadPrice(), bigDecimalToString);
    }

    @Test
    public void threads(){
        Thread th1 = new Thread(() -> transform(downloadDescription(), stringToString));
        Thread th2 = new Thread(() -> transform(downloadPhotos(), stringToString));
        Thread th3 = new Thread(() -> transform(downloadAdditionalData(), longToString));
        Thread th4 = new Thread(() -> transform(downloadPrice(), bigDecimalToString));
        ArrayList<Thread> threads = Lists.newArrayList(th1, th2, th3, th4);
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join(); //czekamy na zakonczenie watku
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void futures(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String> descr = executorService.submit(() -> downloadDescription());
        Future<String> photos = executorService.submit(() -> downloadPhotos());
        Future<Long> addData = executorService.submit(() -> downloadAdditionalData());
        Future<BigDecimal> price = executorService.submit(() -> downloadPrice());
        executorService.submit(() -> transform(descr.get(), stringToString));
        executorService.submit(() -> transform(photos.get(), stringToString));
        executorService.submit(() -> transform(addData.get(), longToString));
        executorService.submit(() -> transform(price.get(), bigDecimalToString));

        executorService.shutdown();
        while(!executorService.isTerminated()){

        }

    }

    @Test
    public void completableFutures(){
        CompletableFuture<String> descriptionSource1 = CompletableFuture.supplyAsync(this::downloadDescription);
        CompletableFuture<String> descriptionSource2 = CompletableFuture.supplyAsync(this::downloadDescription2);
        CompletableFuture<String> descrCF = descriptionSource1.applyToEitherAsync(descriptionSource2, e -> e).thenApplyAsync(e->transform(e,stringToString));
        CompletableFuture<String> phCF = CompletableFuture.supplyAsync(() -> downloadPhotos()).thenApplyAsync(e->transform(e,stringToString));
        CompletableFuture<String> adataCF = CompletableFuture.supplyAsync(() -> downloadAdditionalData()).thenApplyAsync(e->transform(e,longToString));
        CompletableFuture<String> priceCF = CompletableFuture.supplyAsync(() -> downloadPrice()).thenApplyAsync(e->transform(e,bigDecimalToString));
        List<CompletableFuture<String>> completableFutureList = Arrays.asList(descrCF, phCF, adataCF, priceCF);
        for (CompletableFuture<String> cf : completableFutureList) {
            cf.join();
        }
    }

    private void simulateDelay(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String downloadDescription() {
        simulateDelay(2000);
        return "opis";
    }

    public String downloadDescription2() {
        simulateDelay(3000);
        return "opis";
    }

    public BigDecimal downloadPrice() {
        simulateDelay(2500);
        return BigDecimal.valueOf(3000.2);
    }

    public String downloadPhotos() {
        simulateDelay(3000);
        return "photos";
    }

    public Long downloadAdditionalData() {
        simulateDelay(3300);
        return 3000L;
    }


    @AllArgsConstructor
    @ToString
    private class ProductForTest {
        private String description;
        private String price;
        private String photos;
        private String additionalData;
    }
}
