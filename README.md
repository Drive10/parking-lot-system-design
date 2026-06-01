# Parking Lot System

[![CI](https://github.com/yourusername/parking-lot-system-design/actions/workflows/ci.yml/badge.svg)](https://github.com/yourusername/parking-lot-system-design/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://adoptium.net/)

Production-grade Parking Lot Management System built with **Spring Boot 3**, **JPA/Hibernate**, **Flyway**, **PostgreSQL**, and **REST API** — applying **SOLID principles** and **Strategy pattern**.

## Features

- **REST API** — Park, unpark, pay, check status via HTTP endpoints
- **Multi-floor** parking lot with configurable spots
- **Multiple vehicle types** — CAR, BIKE, TRUCK with distinct hourly rates
- **Pluggable strategies** — Nearest First / Max Availability for spot assignment
- **Fee calculation** — Hourly rate with minimum fee, easy to extend
- **Payment processing** — CASH, CREDIT_CARD, DEBIT_CARD, UPI, PAYPAL
- **Database persistence** — JPA entities, Flyway migrations, PostgreSQL (H2 for dev)
- **Validation** — Bean Validation on all API inputs
- **Error handling** — Global exception handler with structured error responses
- **API docs** — Swagger UI / OpenAPI 3
- **Monitoring** — Spring Boot Actuator health checks
- **Docker support** — Dockerfile + compose.yaml for PostgreSQL
- **Profile-aware** — dev (H2), test (H2), prod (PostgreSQL)

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.3, Spring MVC |
| Database | PostgreSQL 16 (prod), H2 (dev/test) |
| ORM | Hibernate 6 + Spring Data JPA |
| Migrations | Flyway |
| API Docs | SpringDoc OpenAPI 2 |
| Monitoring | Spring Boot Actuator |
| Container | Docker + Docker Compose |
| Testing | JUnit 5, TestRestTemplate, Testcontainers |
| Build | Maven |

## Quick Start

```bash
# Development (H2 in-memory)
make dev

# Or with Maven
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Production with Docker
make docker-up
```

## API Endpoints

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/v1/parking/park` | Park a vehicle |
| `POST` | `/api/v1/parking/unpark/{ticketUid}` | Unpark and calculate fee |
| `GET` | `/api/v1/parking/tickets/{ticketUid}` | Get ticket info |
| `POST` | `/api/v1/payments/{ticketUid}?method=CASH` | Process payment |
| `GET` | `/api/v1/lots/{lotId}/status` | Parking lot status |
| `GET` | `/swagger-ui.html` | Swagger UI |
| `GET` | `/actuator/health` | Health check |

### Example: Park a Vehicle

```bash
curl -s -X POST http://localhost:8080/api/v1/parking/park \
  -H "Content-Type: application/json" \
  -d '{"registrationNumber":"KA-01-1234","color":"White","vehicleType":"CAR"}'
```

```json
{
  "ticketUid": "3F8A2B-1234",
  "registrationNumber": "KA-01-1234",
  "vehicleColor": "White",
  "vehicleType": "CAR",
  "spotIdentifier": "G-C1",
  "floorNumber": 0,
  "entryTime": "2024-01-01T12:00:00",
  "fee": 0.0,
  "isPaid": false,
  "status": "ACTIVE"
}
```

### Example: Unpark

```bash
curl -s -X POST http://localhost:8080/api/v1/parking/unpark/3F8A2B-1234
```

## Project Structure

```
src/main/java/com/parking/
├── ParkingLotApplication.java       # Spring Boot entry point
├── config/
│   ├── OpenApiConfig.java           # Swagger configuration
│   └── DataInitializer.java         # Seed data on startup
├── constants/
│   ├── VehicleType.java             # CAR, BIKE, TRUCK with rates
│   ├── ParkingSpotType.java
│   ├── TicketStatus.java
│   ├── PaymentMethod.java
│   └── PaymentStatus.java
├── entity/                          # JPA entities
│   ├── ParkingLotEntity.java
│   ├── ParkingFloorEntity.java
│   ├── ParkingSpotEntity.java
│   ├── VehicleEntity.java
│   ├── TicketEntity.java
│   └── PaymentEntity.java
├── repository/                      # Spring Data JPA repositories
├── dto/
│   ├── request/                     # ParkVehicleRequest, PaymentRequest
│   └── response/                    # TicketResponse, ParkingLotStatusResponse
├── service/                         # Business logic layer
│   ├── ParkingService.java          # Core parking operations
│   ├── SpotAssignmentService.java   # Strategy-driven spot finding
│   ├── TicketService.java           # Ticket lifecycle
│   ├── PaymentService.java          # Payment processing
│   ├── VehicleService.java          # Vehicle lookup/create
│   └── FeeCalculator.java           # Fee computation
├── strategy/                        # Strategy pattern implementations
│   ├── ParkingStrategy.java         # Interface
│   ├── NearestFirstStrategy.java    # Closest spot first
│   ├── MaxAvailabilityStrategy.java # Spread across floors
│   ├── FeeCalculationStrategy.java  # Interface
│   └── HourlyFeeStrategy.java       # Per-hour rate
├── exception/                       # Custom exceptions + global handler
└── controller/                      # REST controllers

src/main/resources/
├── application.yml                  # Default config
├── application-dev.yml              # H2 in-memory
├── application-prod.yml             # PostgreSQL
├── application-test.yml             # Test config
└── db/migration/
    └── V1__init_schema.sql          # Flyway migration

src/test/java/com/parking/
├── ParkingLotApplicationTests.java  # Context load test
├── controller/
│   └── ParkingControllerIntegrationTest.java  # REST API tests
└── strategy/
    ├── NearestFirstStrategyTest.java
    └── HourlyFeeStrategyTest.java
```

## Design Patterns

| Pattern | Usage |
|---|---|
| **Strategy** | Pluggable parking spot assignment and fee calculation |
| **DAO / Repository** | Spring Data JPA repositories |
| **DTO** | Request/response objects separate from entities |
| **Builder** | Entity construction with builder pattern |

## Commands

```bash
make build        # mvn clean package -DskipTests
make test         # mvn test
make dev          # Run with dev profile (H2)
make docker-up    # Run with Docker Compose (PostgreSQL)
make docker-down  # Stop Docker containers
```
