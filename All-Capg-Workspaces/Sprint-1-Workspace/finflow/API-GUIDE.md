# FinFlow API Quick Start & Flow

This guide describes how to start the environment and verify the core business APIs.

## Architecture & Ports
- **Gateway**: http://localhost:8090/gateway
- **Auth**: http://localhost:8081
- **Application**: http://localhost:8082
- **Document**: http://localhost:8083
- **Admin**: http://localhost:8084
- **Eureka**: http://localhost:8761
- **Zipkin (Tracing)**: http://localhost:9411
- **Swagger (API Docs)**: http://localhost:8090/swagger-ui.html

## 1. Environment Startup
To bring up all containers (MySQL, RabbitMQ, and the microservices):
```bash
docker-compose up -d --build
```
Verify health: `docker-compose ps`

## 2. API Functional Flow
Follow this sequence to test a complete loan application workflow.

### Step 1: User Signup (Auth)
Create a new user account as an APPLICANT.
- **POST**: `http://localhost:8090/gateway/auth/signup`
- **Body**:
```json
{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

### Step 2: User Login (Auth)
Authenticate to receive a JWT Bearer token.
- **POST**: `http://localhost:8090/gateway/auth/login`
- **Body**:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```
**Important**: Copy the `token` from the response. Add it as `Authorization: Bearer <token>` for all subsequent steps.

### Step 3: Create Draft Application (Application Service)
Initialize a new loan application. It will start in `DRAFT` status.
- **POST**: `http://localhost:8090/gateway/applications/draft`
- **Headers**: `Authorization: Bearer <token>`
- **Body**: `{}` (Can be empty)

### Step 4: Update Application Data (Application Service)
Fill in the details for your application.
- **PUT**: `http://localhost:8090/gateway/applications/{id}/draft`
- **Headers**: `Authorization: Bearer <token>`
- **Body**:
```json
{
  "fullName": "John Doe",
  "phone": "9876543210",
  "address": "123 Main St, Springfield",
  "dateOfBirth": "1990-01-01",
  "employer": "Tech Global",
  "annualIncome": 1200000,
  "employmentType": "SALARIED",
  "loanAmount": 500000,
  "tenureMonths": 24,
  "loanPurpose": "Personal"
}
```

### Step 5: Submit Application (Application Service)
Submit the completed draft for processing.
- **POST**: `http://localhost:8090/gateway/applications/{id}/submit`
- **Headers**: `Authorization: Bearer <token>`

### Step 6: Document Upload (Document Service)
Upload proof of identity or income (ID_PROOF).
- **POST**: `http://localhost:8090/gateway/documents/upload`
- **Headers**: `Authorization: Bearer <token>`
- **Multipart Data**:
    - `file`: (Binary File)
    - `applicationId`: {id}
    - `docType`: "ID_PROOF"

### Step 7: Admin Verification (Admin Service)
Verify the final status as an administrator.
- **GET**: `http://localhost:8090/gateway/admin/applications`
- **Headers**: `Authorization: Bearer <AdminToken>`

## 3. Database Management
If you need to manually promote a user to **ADMIN**, run:
```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'user@example.com';
```
This is done directly in the MySQL database (Port 3307).

## 4. RabbitMQ Integration Testing
FinFlow uses RabbitMQ for asynchronous status updates. You can verify this manually:

### Step 1: Access Management UI
- **URL**: http://localhost:15672
- **Credentials**: `guest` / `guest`
- **Verification**: Check the **Exchanges** tab for `finflow.application.events` and **Queues** for `finflow.app.status.queue`.

### Step 2: Trigger an Event
Perform **Step 5 (Submit Application)** from the functional flow above. This triggers a `SUBMITTED` then `DOCS_PENDING` status change.

### Step 3: Verify Asynchronous Delivery
Check the logs of the consumer services to see the message being processed:
```bash
docker-compose logs --tail 20 finflow-document-service
docker-compose logs --tail 20 finflow-admin-service
```
You should see: `[DocumentService][RabbitMQ] Received event – appId=...`

## 5. API Documentation (Swagger)
You can view and test all microservice APIs through the unified Swagger UI:
- **URL**: http://localhost:8090/swagger-ui.html
- **Usage**: Select the service definition from the top-right dropdown to see its endpoints.

## 6. Distributed Tracing (Zipkin)
Monitor the flow of requests across microservices:
- **URL**: http://localhost:9411
- **Verification**: Perform some actions in Swagger UI, then "Run Query" in Zipkin to see the traces.
