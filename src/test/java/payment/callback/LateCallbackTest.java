package payment.callback;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class LateCallbackTest {

    private static final Map<String, String> transactionState = new HashMap<>();

    @Test
    public void lateCallbackShouldNotChangeFinalState() {
        String transactionId = "trx-late-001";
        
        transactionState.put(transactionId, "FAIILED_TIMEOUT");

        handleCallback(transactionId, "SUCCESS");

        assertEquals(
            transactionState.get(transactionId),
            "FAILED TIMEOUT",
            "Late callback must not override final transaction state"
        );
    }

    private void handleCallback(String transactionId, String callbackStatus) {
        String currentState = transactionState.get(transactionId);

        if ("FAILED_TIMEOUT".equals(currentState)) {
            return;
        }
        transactionState.put(transactionId, callbackStatus);
    }
    
}
