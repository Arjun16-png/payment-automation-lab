package payment.invariant;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.fail;
import org.testng.annotations.Test;

public class PaymentInvariantTest {
    
    @Test
    public void paymentAmountMustAlwaysBePositive(){
        int amount = -1000;

        try {
            validateAmount(amount);
            fail("Negative amount must be rejected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("amount"));
        }
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid payment amount");
        }
    }
}
