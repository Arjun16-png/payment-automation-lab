package payment.callback;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.Test;

public class AmmountMismatchCallbackTest {
    private static final Map<String, Integer> originalAmmount = new HashMap<>();
    private static final Map<String, String> transactionState = new HashMap<>();

    @Test
    public void callbackWithDifferentAmountShouldBeRejected() {
        String transactionId = "trx-amt-001";

        originalAmmount.put(transactionId, 10000);
        
        boolean accepted = handleCallback(transactionId, 12000, "SUCCESS");

        assertFalse(accepted, "Callback must be rejected when amount mismatches");
        assertEquals(transactionState.get(transactionId), null, "Rejected callback");
    }

    private boolean handleCallback(String transactionId, int callbackAmount, String status) {
        Integer expectedAmount = originalAmmount.get(transactionId);

        if (expectedAmount == null || expectedAmount != callbackAmount) {
            return false;
        }

        transactionState.put(transactionId, status);
        return true;
    }
}
