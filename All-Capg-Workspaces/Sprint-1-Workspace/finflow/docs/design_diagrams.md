# FinFlow Design Documentation

This document provides a visual representation of the FinFlow Loan Management System's functional requirements, system architecture, and data model.

---

## 1. Use Case Diagram

The Use Case diagram illustrates the primary interactions between the actors (**Applicant**, **Admin**) and the core functionalities of the FinFlow ecosystem.

![Use Case Diagram](./assets/use_case.png)

### Mermaid Source
```mermaid
useCaseDiagram
    actor Applicant as "Applicant (User)"
    actor Admin as "Loan Officer (Admin)"
    
    package "FinFlow Ecosystem" {
        usecase UC1 as "Secure Login/Registration"
        usecase UC2 as "Create Loan Application"
        usecase UC3 as "Upload Required Documents"
        usecase UC4 as "Track Application Status"
        usecase UC5 as "Receive Real-time Alerts"
        
        usecase UC6 as "View All Applications"
        usecase UC7 as "Review & Verify Documents"
        usecase UC8 as "Approve/Reject Application"
        usecase UC9 as "Monitor System Health (Zipkin/Eureka)"
    }
    
    Applicant --> UC1
    Applicant --> UC2
    Applicant --> UC3
    Applicant --> UC4
    Applicant --> UC5
    
    Admin --> UC1
    Admin --> UC6
    Admin --> UC7
    Admin --> UC8
    Admin --> UC9
```

---

## 2. Architecture Diagram

FinFlow follows a **Microservices Architecture** with a centralized API Gateway, Service Discovery, and an Event-Driven notification system.

![Architecture Diagram](./assets/architecture.png)

### Mermaid Source
```mermaid
graph TD
    Client((User Browser)) -->|Angular 18+| UI[FinFlow UI - Nginx]
    UI -->|REST/JWT| GW[Spring Cloud Gateway]
    
    subgraph "Core Microservices"
        GW --> Eureka[Eureka Discovery]
        GW --> Auth[Auth Service - JWT/RBAC]
        GW --> App[Application Service - Loan Lifecycle]
        GW --> Doc[Document Service - File Management]
        GW --> Adm[Admin Service - Workflow Processing]
    end
    
    subgraph "Infrastructure & Support"
        Auth & App & Doc & Adm & Notif --> MySQL[(MySQL 8.0 Database)]
        App & Doc & Adm -.->|Publish Events| RMQ[RabbitMQ Broker]
        RMQ -.->|Consume| Notif[Notification Service]
        AllServices[All Microservices] --> Zipkin[Zipkin Tracing]
    end
    
    style UI fill:#f9f,stroke:#333,stroke-width:2px
    style GW fill:#bbf,stroke:#333,stroke-width:2px
    style MySQL fill:#dfd,stroke:#333,stroke-width:2px
    style RMQ fill:#ffd,stroke:#333,stroke-width:2px
```

---

## 3. Database Diagram (Logical ERD)

The following diagram represents the logical data model across the various microservices.

![Database ERD](./assets/database.png)

### Mermaid Source
```mermaid
erDiagram
    USERS ||--o{ LOAN_APPLICATIONS : "applies"
    USERS {
        bigint id PK
        string email UK
        string password_hash
        string first_name
        string last_name
        string role
        boolean enabled
    }

    LOAN_APPLICATIONS ||--o{ DOCUMENTS : "requires"
    LOAN_APPLICATIONS {
        bigint id PK
        bigint user_id FK
        string status
        decimal loan_amount
        string loan_purpose
        int tenure_months
        datetime created_at
    }

    DOCUMENTS {
        bigint id PK
        bigint application_id FK
        string file_name
        string file_type
        string storage_path
        string status
    }

    LOAN_APPLICATIONS ||--o{ DECISIONS : "receives"
    DECISIONS {
        bigint id PK
        bigint application_id FK
        bigint admin_id FK
        string status
        string notes
        datetime created_at
    }
```

---

## 4. Exception Handling Design

FinFlow implements a **Global Exception Handling** strategy using Spring Boot's `@RestControllerAdvice`. This ensures that every microservice returns a consistent error structure, regardless of the failure type.

![Exception Handling Flow](./assets/exception_flow.png)

### Exception Workflow
1. **Service Layer**: Throws a custom or standard exception (e.g., `LoanNotFoundException`).
2. **Advice Layer**: The `GlobalExceptionHandler` intercepts the exception before it reaches the client.
3. **DTO Transformation**: The exception is mapped to a standardized `ApiResponse` DTO.
4. **Client Response**: A structured JSON response with a relevant HTTP status code (4xx/5xx) is returned.

### Mermaid Sequence Diagram
```mermaid
sequenceDiagram
    participant Client
    participant Controller
    participant Service
    participant Handler as "GlobalExceptionHandler (@RestControllerAdvice)"

    Client->>Controller: API Request
    Controller->>Service: Process Business Logic
    Service-->>Service: Violation Detected
    Service-->>Handler: Throw Exception
    Handler-->>Handler: Map to Standard Error DTO
    Handler->>Client: structured JSON Response (4xx/5xx)
```

### Standard Error Structure
Every error follows this uniform format:
```json
{
  "success": false,
  "message": "Specific error message here",
  "data": null
}
```

---

## 🛠️ Design Summary

- **Architecture**: Microservices based on Spring Boot 3.x and Spring Cloud.
- **Frontend**: Premium UI developed with Angular 18+, following a modern glassmorphism aesthetic.
- **Security**: JWT-based stateless authentication with role-based routing at both Gateway and UI levels.
- **Messaging**: Asynchronous notifications triggered by status changes in the application and document lifecycles.
- **Observability**: Centralized logging and distributed tracing for debugging complex distributed workflows.
- **Error Handling**: Standardized `@RestControllerAdvice` providing uniform JSON responses across all services.
