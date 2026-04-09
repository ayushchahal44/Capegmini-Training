# FinFlow Microservices

A distributed loan processing system built with Spring Boot and Spring Cloud.

## Project Overview
FinFlow is designed as a series of independent microservices that handle different stages of a loan application, from user authentication to document management and administrative approval.

## Active Microservices
- **Auth Service (8081)**: Manages user registration, login, and JWT token issuance.
- **Admin Service (8084)**: Provides endpoints for system administration and application oversight.
- **Application Service (8082)**: Handles the core business logic for loan drafts and submissions.
- **Document Service (8083)**: Manages file uploads and associations with specific applications.
- **Gateway (8090)**: Central entry point for all requests, providing unified routing via `/gateway/**` paths.
- **Eureka Server (8761)**: Service discovery and registry for the ecosystem.
- **Zipkin Server (9411)**: Distributed tracing server for monitoring requests.
- **Swagger UI**: Integrated into the gateway at `/swagger-ui.html`.

## Technology Stack
- **Spring Boot 3.x**: Chosen for its robust support for production-ready Java services.
- **Spring Cloud Gateway**: Used for centralized routing, which simplifies client interaction and security.
- **Spring Cloud Eureka**: Essential for service discovery in a dynamic microservice environment.
- **MySQL 8.0**: Reliable relational storage for business data (users, applications, logs).
- **RabbitMQ**: Active for asynchronous event-driven communication between services.
- **Docker & Docker Compose**: Used to ensure environment consistency across development and production.

## System Architecture
The system follows a typical microservice pattern:
1. Clients interact primarily with the **Gateway**.
2. Services register themselves with **Eureka** upon startup.
3. The Gateway lookups service locations in Eureka and routes traffic accordingly.
4. Services use shared libraries (`finflow-common`) for JWT logic and standard DTOs.

## Getting Started
To start the entire ecosystem, ensure you have Docker installed and run:
```bash
docker-compose up -d --build
```
The services will initialize and register with Eureka automatically.
