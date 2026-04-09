# Hotel Management System - Spring Boot

## Project Overview

This project is a **Hotel Management System** developed using **Spring Boot, Spring Data JPA, and Oracle Database**.
It allows administrators to manage hotel rooms and customer bookings through REST APIs.

The application follows a **3-layer architecture**:

* **Controller Layer** – Handles HTTP requests
* **Service Layer** – Business logic
* **Repository Layer** – Database interaction using Spring Data JPA

---

# Technology Stack

* Java 17
* Spring Boot
* Spring Data JPA
* Oracle Database
* Maven
* REST APIs
* Tomcat (Embedded)

---

# Project Structure

```
com.hotelmanagement

controller
    RoomController
    BookingController

service
    RoomService
    BookingService

service.impl
    RoomServiceImpl
    BookingServiceImpl

repository
    RoomRepository
    BookingRepository

entity
    Room
    Booking

HotelManagementApplication
```

---

# Database Setup

Create the following tables in **Oracle Database**.

## Room Table

```sql
CREATE TABLE ROOM (
ROOM_ID NUMBER PRIMARY KEY,
ROOM_NUMBER VARCHAR2(20),
ROOM_TYPE VARCHAR2(20),
PRICE NUMBER,
STATUS VARCHAR2(20)
);
```

## Booking Table

```sql
CREATE TABLE BOOKING (
BOOKING_ID NUMBER PRIMARY KEY,
CUSTOMER_NAME VARCHAR2(100),
CUSTOMER_PHONE VARCHAR2(20),
CHECKIN_DATE DATE,
CHECKOUT_DATE DATE,
ROOM_ID NUMBER,
CONSTRAINT FK_ROOM
FOREIGN KEY (ROOM_ID)
REFERENCES ROOM(ROOM_ID)
);
```

---

# Application Configuration

Add the following configuration in **application.properties**

```
server.port=9092

spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=CAPGDB
spring.datasource.password=CAPGDB
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# How to Run the Project

1. Open the project in **Spring Tool Suite / Eclipse / IntelliJ**
2. Ensure **Oracle Database is running**
3. Configure **application.properties**
4. Run the main class:

```
HotelManagementApplication.java
```

5. Server will start on:

```
http://localhost:9092
```

---

# API Endpoints

## Room APIs

### 1. Add Room

POST
`/rooms`

Example Request Body

```json
{
  "roomId": 1,
  "roomNumber": "101",
  "roomType": "Deluxe",
  "price": 2000,
  "status": "Available"
}
```

---

### 2. Get All Rooms

GET
`/rooms`

---

### 3. Get Room By ID

GET
`/rooms/{id}`

Example

```
/rooms/1
```

---

### 4. Update Room

PUT
`/rooms/{id}`

Example

```
/rooms/1
```

Request Body

```json
{
  "roomNumber": "101",
  "roomType": "Suite",
  "price": 3500,
  "status": "Available"
}
```

---

### 5. Delete Room

DELETE
`/rooms/{id}`

Example

```
/rooms/1
```

---

# Booking APIs

### 1. Create Booking

POST
`/bookings?roomId={roomId}`

Example

```
/bookings?roomId=1
```

Request Body

```json
{
  "bookingId": 1,
  "customerName": "Ayush",
  "customerPhone": "9876543210",
  "checkInDate": "2026-03-10",
  "checkOutDate": "2026-03-12"
}
```

---

### 2. Get All Bookings

GET
`/bookings`

---

### 3. Get Booking By ID

GET
`/bookings/{id}`

Example

```
/bookings/1
```

---

### 4. Update Booking

PUT
`/bookings/{id}`

Example

```
/bookings/1
```

Request Body

```json
{
  "customerName": "Ayush Chahal",
  "customerPhone": "9999999999",
  "checkInDate": "2026-03-11",
  "checkOutDate": "2026-03-13"
}
```

---

### 5. Cancel Booking

DELETE
`/bookings/{id}`

Example

```
/bookings/1
```

---

# Features

* Room Management (CRUD)
* Booking Management (CRUD)
* Room-Booking Relationship
* Oracle Database Integration
* RESTful API Design
* Layered Architecture

---

# Author

**Ayush Chahal**

Software Engineer
Spring Boot | Java
