package payment.idempotency;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class DuplicateCallbackTest {

    private static final Set<String> processedTransactions = new HashSet<>();
    
    @Test
    public void duplicateCallbackShouldNotDoubleProcess() {
        String transactionId = "trx-12345";

        processCallback(transactionId);

        processCallback(transactionId);

        assertEquals(processedTransactions.size(), 1,
            "Duplicate callback!!");
    }

    private void processCallback(String transactionId) {
        if (!processedTransactions.contains(transactionId)) {
            processedTransactions.add(transactionId);
        }
    }
    

}
