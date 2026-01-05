package payment.concurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class ConcurrentCallbackTest {

    private static final ConcurrentMap<String, Boolean> processed = new ConcurrentHashMap<>();

    @Test
    public void concurrentCallbackShouldProcessOnlyOnce() throws InterruptedException {
        int threads = 2;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        Runnable task = () -> {
            processCallback("trx-999");
            latch.countDown();
        };

        executor.submit(task);
        executor.submit(task);

        latch.await(2, TimeUnit.SECONDS);
        executor.shutdown();

        assertEquals(processed.size(), 1, "Concurrent callback must be processed only once");

    }

    private void processCallback(String transactionId) {
        Boolean alreadyProcessed = processed.putIfAbsent(transactionId, true);
        if (alreadyProcessed != null) {
            return;
        }
    }
    
}
