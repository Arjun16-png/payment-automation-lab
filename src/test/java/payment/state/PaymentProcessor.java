package payment.state;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PaymentProcessor {

    private static final ConcurrentMap<String, PaymentState> states =
        new ConcurrentHashMap<>();
    
    public static void processCallback(String trxId, PaymentState incomingState) {
        states.compute(trxId, (id, currentState) -> {
            if (currentState == null) {
                return incomingState;
            }

            if (currentState.canTransitionTo(incomingState)) {
                return incomingState;
            }

            return currentState;
        });

    }

    public static PaymentState getsState(String trxId) {
        return states.get(trxId);
    }

    public static void reset() {
        states.clear();
    }
}
    
