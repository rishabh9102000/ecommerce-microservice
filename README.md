# E-Commerce Microservices

## Overview
A microservices-based e-commerce backend built with Spring Boot, simulating an
end-to-end order processing pipeline across 4 independently running services.

## Services
- order-service (port 8080)
- inventory-service (port 8081)
- payment-service (port 8082)
- notification-service (port 8083)

## Architecture

Client (Postman)

↓

Order Service (8080)  ←── Main entry point

↓

Inventory Service (8081) ←── Checks product availability

↓

Payment Service (8082)   ←── Simulates payment processing

↓

Notification Service (8083) ←── Sends EMAIL/SMS notifications

Inter-service communication via REST (RestTemplate). Order orchestration
uses CompletableFuture with a custom ThreadPoolExecutor to free Spring's
HTTP thread during blocking I/O calls.

## Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Web (RestTemplate)
- Lombok
- SLF4J + Logback
- Maven
- Postman (testing)
- Git/GitHub

## Design Patterns
- **Builder** — `Order` object construction with default UUID and timestamp
- **Factory** — `PaymentFactory` and `NotificationFactory` return the correct
  strategy implementation based on input type
- **Strategy** — `PaymentStrategy` (UPI, CreditCard, Wallet) and
  `NotificationStrategy` (Email, SMS) define interchangeable algorithms

## Key Features
- **Async orchestration** — CompletableFuture chain (supplyAsync → thenApplyAsync
  → thenAcceptAsync) on a custom ThreadPoolExecutor (core=10, max=20, queue=50),
  freeing Spring's HTTP thread during I/O wait
- **Correlation IDs** — UUID generated per request in OrderController, propagated
  as `X-Correlation-ID` header to all downstream services, logged at every step
  for distributed tracing
- **Centralized exception handling** — `@RestControllerAdvice` in each service
  returns structured JSON error responses
- **Input validation** — `@Valid` with `@NotBlank`/`@Min` on OrderDto, clean
  validation error messages via `MethodArgumentNotValidException` handler
- **Structured logging** — custom `logback-spring.xml` pattern:
  `timestamp | level | correlationId | thread | message`

## How to Run
1. Clone the repository
2. Open all 4 services in IntelliJ as separate modules
3. Start each service in this order:
    - `InventoryApplication` (port 8081)
    - `PaymentApplication` (port 8082)
    - `NotificationApplication` (port 8083)
    - `OrderApplication` (port 8080)

## API Endpoints

### Place an Order
`POST http://localhost:8080/api/orders`
```json
{
  "productId": "P101",
  "userId": "U001",
  "quantity": 2
}
```

### Check Inventory
`GET http://localhost:8081/api/inventory/check?productId=P101&quantity=2`

### Process Payment
`POST http://localhost:8082/api/payments/process`
```json
{
  "orderId": "ORD123",
  "userId": "U001",
  "paymentMode": "UPI",
  "senderAccount": "ACC001",
  "receiverAccount": "ACC002",
  "amount": 500.0
}
```

### Send Notification
`POST http://localhost:8083/api/notifications/send`
```json
{
  "userId": "U001",
  "message": "Your order is confirmed",
  "type": "EMAIL"
}
```

## Test Scenarios
| Scenario | Input | Expected Response |
|---|---|---|
| Happy path | productId: P101, quantity: 2 | 200 `CONFIRMED` |
| Out of stock | productId: P102, quantity: 1 | 400 `ORDER_ERROR` |
| Validation failure | missing userId | 400 `VALIDATION_FAILED` |
| Payment failure | random 30% chance | 400 `ORDER_ERROR` |

## Stage 2 (Planned)
- Replace RestTemplate with Kafka event-driven communication
- React frontend for order placement
- Spring Security for authentication
- Docker deployment
