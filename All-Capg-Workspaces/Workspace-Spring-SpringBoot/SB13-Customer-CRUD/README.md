SB13 Customer CRUD Application

A Spring Boot REST API application for performing CRUD operations on Customer data using JPA EntityManager and Oracle Database.
This project demonstrates a layered architecture (Controller → Service → DAO → Entity).

----------------------------------------------------

Tech Stack

- Java 17
- Spring Boot
- Spring Web
- JPA (EntityManager)
- Hibernate
- Oracle 11g XE
- Maven
- Postman (API Testing)

----------------------------------------------------

Project Architecture

com.capg.springboot
│
├── controller
│       CustomerController
│
├── service
│       CustomerService
│       CustomerServiceImpl
│
├── dao
│       CustomerDAO
│       CustomerDAOImpl
│
├── entity
│       Customer
│
├── exception
│       CustomerNotFoundException
│
└── Sb13CustomerCrudApplication

----------------------------------------------------

Database Configuration

application.properties

server.port=9090

spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=system
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

----------------------------------------------------

Customer Entity Fields

custid
cname
address

----------------------------------------------------

API Endpoints

Base URL

http://localhost:9090

----------------------------------------------------

Add Customer

POST
http://localhost:9090/customer/add

Body

{
  "custid": 1,
  "cname": "Ayush",
  "address": "Delhi"
}

----------------------------------------------------

Get All Customers

GET
http://localhost:9090/customer/all

----------------------------------------------------

Search Customer by ID

GET
http://localhost:9090/customer/search/{id}

Example

http://localhost:9090/customer/search/1

----------------------------------------------------

Update Customer

PUT
http://localhost:9090/customer/update

Body

{
  "custid": 1,
  "cname": "Ayush Chahal",
  "address": "Bijnor"
}

----------------------------------------------------

Delete Customer

DELETE
http://localhost:9090/customer/delete/{id}

Example

http://localhost:9090/customer/delete/1

----------------------------------------------------

How to Run the Project

1. Clone the repository

git clone <repo-url>

2. Open project in Spring Tool Suite / IntelliJ / Eclipse

3. Configure Oracle database credentials in application.properties

4. Run the main class

Sb13CustomerCrudApplication.java

5. Test APIs using Postman

----------------------------------------------------

Features

- REST API using Spring Boot
- CRUD operations using JPA EntityManager
- Layered architecture
- Oracle database integration
- JSON request and response handling

----------------------------------------------------

Author

Ayush Chahal