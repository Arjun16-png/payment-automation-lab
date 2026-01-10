package payment.state;

public enum PaymentState {
    PENDING(1),
    AUTHORIZED(2),
    SUCCESS(3),
    FAILED(4);

    private final int priority;

    private PaymentState(int priority) {
        this.priority = priority;
    }

    public boolean canTransitionTo(PaymentState next) {
        return next.priority >= this.priority;
    }

    
    
}
