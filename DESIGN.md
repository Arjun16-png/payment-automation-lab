# Design Principles

This project models real-world payment callback behavior with a strong emphasis on correctness and safety.

## Core Principles

### 1. Exactly-once Processing
Callbacks may be duplicated or retried.
Idempotency keys and atomic operations ensure each transaction is processed only once.

### 2. State Ordering Guarantees
Payment callbacks are not guaranteed to arrive in order.
State transitions are guarded to prevent regression (e.g. SUCCESS → PENDING).

### 3. Concurrency Safety
All shared state mutations use atomic operations to avoid race conditions under parallel execution.

### 4. Defensive Validation
Callbacks are validated before any state mutation:
- Signature authenticity
- Amount consistency
- Valid state transitions

### 5. Bounded Retries
Retries are limited and paired with idempotency to avoid infinite loops and duplicate effects.

### 6. Lifecycle Awareness
Idempotency keys use TTL to prevent unbounded memory growth.

## Non-goals

- Live payment provider integration
- UI automation
- Infrastructure-heavy components (DB, Redis, Kafka)

These trade-offs are intentional to keep the focus on payment logic and behavior.
