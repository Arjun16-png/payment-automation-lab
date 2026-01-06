package payment.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class CallbackSignatureTest {
    
    private static final String SECRET = "super-secret-key";
    private static final String BASE_PAYLOAD = "{\"trxId\":\"%s\",\"amount\":%d}";
    

    @Test
    public void validSignatureShouldPass() {
        String payload = payload("trx-100", 10000);
        String signature = generateSignature(payload);

        assertTrue(verifySignature(payload, signature),
            "Valid signature should be accepted");
    }

    @Test
    public void invalidSignatureShouldFail() {
        String payload = payload("trx-100", 10000);
        String invalidSignature = "invalid-signature";
        
        assertFalse(verifySignature(payload, invalidSignature),
            "Invalid signature rejected");
    }

    @Test
    public void tamperedPayShouldFail() {
        String payload = payload("trx-100", 10000);
        String signature = generateSignature(payload);

        String tamperedPayload = payload("trx-100", 9999);

        assertFalse(
            verifySignature(tamperedPayload, signature),
            "Tampered payload must be rejected"
        );
    }

    private boolean verifySignature(String payload, String signature) {
        return generateSignature(payload).equals(signature);
    }

    private String generateSignature(String payload) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(
                SECRET.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
            ));

            byte[] rawHmac = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate signature", e);
        }
    }

    private String payload(String trxId, int amount) {
        return String.format(BASE_PAYLOAD, trxId, amount);
    }
}
