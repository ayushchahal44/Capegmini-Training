# BookStore Microservices – Spring Cloud CRUD System

Repository Name: **bookstore-microservices-ayush**

## Author

Ayush Chahal

---

# Project Overview

This project implements a **Spring Cloud Microservices architecture** for a BookStore system.

The system consists of **four microservices**:

1. **Eureka Server** – Service discovery
2. **Book Service** – Manages book catalog (CRUD)
3. **Order Service** – Manages customer orders
4. **API Gateway** – Single entry point for all APIs

Order Service communicates with Book Service using **OpenFeign Client**.

---

# Architecture

```
Client
   |
   ▼
API Gateway (8090)
   |
   ▼
Eureka Server (8761)
   |
   ├── Book Service (8081)
   └── Order Service (8082)
```

Each service registers itself with **Eureka Service Registry**.

---

# Technologies Used

* Java 17
* Spring Boot 3
* Spring Cloud
* Eureka Server
* OpenFeign Client
* Spring Cloud Gateway
* Spring Data JPA
* H2 Database
* Maven

---

# Microservices Description

## 1. Eureka Server

Acts as the **service registry**.

Port: **8761**

URL:

```
http://localhost:8761
```

All services register themselves here.

---

## 2. Book Service

Handles **book catalog management**.

Port: **8081**

Base URL:

```
/api/books
```

Endpoints:

GET /api/books
GET /api/books/{id}
POST /api/books
PUT /api/books/{id}
DELETE /api/books/{id}

Example Request:

```
POST /api/books
```

```
{
"title":"Clean Code",
"author":"Robert C Martin",
"isbn":"9780132350884",
"price":499.99,
"quantity":20,
"category":"Programming"
}
```

---

## 3. Order Service

Handles **book order management**.

Port: **8082**

Base URL:

```
/api/orders
```

Order Service calls **Book Service using Feign Client** to fetch book details.

Endpoints:

GET /api/orders
GET /api/orders/{id}
POST /api/orders
PUT /api/orders/{id}
DELETE /api/orders/{id}

Example Request:

```
POST /api/orders
```

```
{
"bookId":1,
"customerName":"Alice",
"quantity":2
}
```

Expected Response:

```
{
"id":1,
"bookId":1,
"customerName":"Alice",
"quantity":2,
"totalPrice":999.98,
"status":"PLACED"
}
```

---

## 4. API Gateway

Acts as the **single entry point for all APIs**.

Port: **8090**

Routes:

```
/api/books/**  → book-service
/api/orders/** → order-service
```

Example:

```
http://localhost:8090/api/books
http://localhost:8090/api/orders
```

---

# How to Run the Project

Start services in this **exact order**:

1. Eureka Server
2. Book Service
3. Order Service
4. API Gateway

---

# Testing APIs

Create Book:

```
POST http://localhost:8090/api/books
```

Get Books:

```
GET http://localhost:8090/api/books
```

Place Order:

```
POST http://localhost:8090/api/orders
```

---

# Conclusion

This project demonstrates:

* Microservices architecture
* Service discovery using Eureka
* API Gateway routing
* Inter-service communication using Feign Client
* Full CRUD operations using Spring Boot

---
