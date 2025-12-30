package payment.callback;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class InvalidSignatureCallback {
    private static final Set<String> processedTransactions = new HashSet<>();

    @Test
    public void callbackWithInvalidSignatureShouldBeRejected() {
        String transactionId = "trx-999";
        String validSignature = "sig-valid";
        String invalidSignature = "sig-invalid";

        String expectedSignature = validSignature;

        boolean accepted = handleCallback(transactionId, invalidSignature, expectedSignature);

        assertEquals(accepted, false, "Callback rejected karena signature invalid");
        assertEquals(processedTransactions.size(), 0, "Rejected callback tidak bisa transaksi");
    }

    private boolean handleCallback(String transactionId, String incomingSignature, String expectedSignature) {
        if (!incomingSignature.equals(expectedSignature)) {
            return false;
        }
        processedTransactions.add(transactionId);
        return true;
    }
}
