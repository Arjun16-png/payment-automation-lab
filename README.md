# Payment Automation Lab
A living automation project for testing payment and transactional systems
using Java, TestNG, and RestAssured.

This repository is designed to continuously grow with real-world payment
scenarios such as callbacks, idempotency, and failure handling.

---

# Tech Stack
- Java 11
- Maven
- TestNG
- RestAssured

---

# Progress Log
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

