package payment.idempotency;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class IdempotencyTtlTest {

    private static final long TTL_SECONDS = 5;

    private final Map<String, Long> idempotencyStore = new ConcurrentHashMap<>();

    @Test
    public void idempotencyKeyShouldExpireAfterTtl() throws InterruptedException {
        String transactionId = "trx-ttl-001";

        boolean first = process(transactionId, now());
        assertEquals(first, true);

        boolean duplicate = process(transactionId, now());
        assertEquals(duplicate, false);

        Thread.sleep((TTL_SECONDS + 1) * 1000);

        boolean afterTtl = process(transactionId, now());
        assertEquals(afterTtl, true);
    }

    private boolean process(String transactionId, long timestamp) {
        Long existing = idempotencyStore.get(transactionId);

        if (existing != null && timestamp - existing <= TTL_SECONDS) {
            return false;
        }

        idempotencyStore.put(transactionId, timestamp);
        return true;
    }

    private long now(){
        return Instant.now().getEpochSecond();
    }
    
}
