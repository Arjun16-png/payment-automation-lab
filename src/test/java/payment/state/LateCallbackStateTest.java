package payment.state;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class LateCallbackStateTest {

    @Test
    public void callbackShouldNotRevertFinalState() {
        PaymentState currentState = PaymentState.SETTLED;

        PaymentState result = processCallback(currentState, PaymentState.PAID);

        assertEquals(result, PaymentState.SETTLED, "Late callback should not revert final state");
    }

    private PaymentState processCallback(
            PaymentState current,
            PaymentState incoming) {
                if (current == PaymentState.SETTLED) {
                    return current;
                }
                return incoming;
            }
    
    enum PaymentState{
        CREATED, PAID, SETTLED
    }
    
}
