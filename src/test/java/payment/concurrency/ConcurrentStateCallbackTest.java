package payment.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import payment.state.PaymentProcessor;
import payment.state.PaymentState;

public class ConcurrentStateCallbackTest {
    
    @Test
    public void concurrentCallbackShouldRespectStateOrdering() throws InterruptedException {
        String trxId = "trx-2001";
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        Runnable successCallback = () -> {
            PaymentProcessor.processCallback(trxId, PaymentState.SUCCESS);
            latch.countDown();
        };

        Runnable pendingCallback = () -> {
            PaymentProcessor.processCallback(trxId, PaymentState.PENDING);
            latch.countDown();
        };

        executor.submit(successCallback);
        executor.submit(pendingCallback);

        latch.await();
        executor.shutdown();

        assertEquals(
            PaymentProcessor.getsState(trxId), 
            PaymentState.SUCCESS,
            "Concurrent out-of-order callbacks must not regress state"
        );
    }
}
