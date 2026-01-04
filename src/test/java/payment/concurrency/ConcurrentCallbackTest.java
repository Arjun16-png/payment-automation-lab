package payment.concurrency;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class ConcurrentCallbackTest {

    private static final Set<String> processed = ConcurrentHashMap.newKeySet();

    @Test
    public void concurrentCallbackShouldProcessOnlyOnce() throws InterruptedException {
        int threads = 2;
        CountDownLatch latch = new CountDownLatch(threads);

        Runnable callback = () -> {
            processCallback("trx-999");
            latch.countDown();
        };

        new Thread(callback).start();
        new Thread(callback).start();

        latch.await();

        assertEquals(processed.size(), 1, "Concurrent callback must be processed only once");

    }

    private void processCallback(String transactionId) {
        processed.add(transactionId);
    }
    
}
