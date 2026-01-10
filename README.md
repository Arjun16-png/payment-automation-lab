# Payment Automation Lab

A focused automation project that simulates **real-world payment gateway behaviors** such as duplicate callbacks, concurrency issues, and data integrity validation.

This repository is **not a tutorial project**.  
It is designed to demonstrate how payment systems should be tested against **failure modes that cause real financial incidents**.

[![CI](https://github.com/Arjun16-png/payment-automation-lab/actions/workflows/ci.yml/badge.svg)](https://github.com/Arjun16-png/payment-automation-lab/actions/workflows/ci.yml)

---

## Why This Project Exists

In real payment systems, the biggest risks are **not happy paths**.

They are caused by:
- duplicate callbacks
- concurrent requests
- delayed webhooks
- invalid or tampered payloads
- inconsistent transaction data
- uncontrolled retries

---

## Core Problems Covered

This lab focuses on **payment-critical risks**:

- **Idempotency**
  - Prevents duplicate processing when callbacks are retried or duplicated
- **Concurrency & Race Conditions**
  - Ensures callbacks processed in parallel still result in exactly-once execution
- **Callback Authenticity**
  - Validates HMAC signatures to prevent spoofed or tampered callbacks
- **Data Integrity**
  - Rejects callbacks with mismatched amounts or invalid fields
- **State Regression Protection**
  - Prevents late callbacks from reverting final transaction states
- **Retry & Backoff**
  - Handles transient failures safely without infinite retries
- **Idempotency Lifecycle (TTL)**
  - Prevents unbounded growth of idempotency keys

---

## Tech Stack

- **Java 11**
- **Maven**
- **TestNG**
- **RestAssured**
- Java Concurrency Utilities (`ExecutorService`, `CountDownLatch`, `ConcurrentHashMap`)

---

## How to Run

Run the full test suite:

```bash
mvn clean test
```

---

## Design Principles
- **Business behavior over framework complexity**
- **Deterministic tests (no flaky concurrency)**
- **Atomic operations over synchronized blocks**
- **Immutable data where possible**
- **Tests model production failure scenarios**
- **This project intentionally avoids heavy infrastructure (databases, Redis, message brokers) to keep the focus on core logic and correctness.**

---

## Scope & Limitations
This project:

- ✅ models real payment failure modes
- ✅ focuses on correctness and safety
- ❌ does not integrate with live payment providers
- ❌ does not include UI or end-to-end infrastructure

---

## Progress Log
- ***2025-12-28*** - Bootstrapped payment automation framework (Java 11, Maven, TestNG) and validated setup with first executable API test
- ***2025-12-29*** - Added duplicate callback idempotency scenario to prevent double transaction processing
- ***2025-12-30*** - Added invalid signature callback to prevent unauthorized transaction updates
- ***2025-12-31*** - Introduced late callback handling to prevent transaction state regression after timeout
- ***2026-01-01*** - Added amount mismatch callback validation to prevent inconsistence transaction update  
- ***2026-01-03*** - Add payment state transition guard for late callback                                          
- ***2026-01-04*** - Add concurrency-safe callback processing test
- ***2026-01-05*** - Refactor idempotent callback handling with atomic concurrency control
- ***2026-01-06*** - Added HMAC callback signature verification to ensure callback authenticity and payload integrity
- ***2026-01-07*** - Add retry logic with exponential backoff for callback handling
- ***2026-01-08*** - Add payment invariant validation test for amount
- ***2026-01-09*** - Add idempotency key TTL handling to prevent unbounded growth
- ***2026-01-10*** — Added payment state ordering and concurrency-safe callback handling to prevent state regression under out-of-order and parallel callbacks
