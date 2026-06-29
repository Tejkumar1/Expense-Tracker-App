# Expense Tracker App - Readme
A full-stack Expense Tracker application built using Spring Boot, MySQL, and React. This project helps users track daily expenses, manage categories, and analyze spending securely with authentication.

# Tech Stack


  # Backend
     
Java 17

Spring Boot

Spring Data JPA

Spring Security (JWT)

MySQL

Maven

# Frontend

ReactJS

JavaScript

HTML & CSS

Axios

# Project Structure

expense-tracker
│── src/main/java/com/expensetracker
│ ├── controller
│ ├── service
│ ├── repository
│ ├── model
│ └── ExpenseTrackerApplication.java
│
│── src/main/resources
│ └── application.properties
│
│── pom.xml


# Configuration
     spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Database Setup
    CREATE DATABASE expense_tracker;

# Prerequisites
       Java 17 installed
       MySQL running
       Maven Wrapper included

# Run Command
     ./mvnw spring-boot:run

# Application URL
    http://localhost:8080

# API Endpoints(Sample)
     | Method | Endpoint         | Description      |
| ------ | ---------------- | ---------------- |
| POST   | `/users`         | Create user      |
| POST   | `/expenses`      | Add expense      |
| GET    | `/expenses`      | Get all expenses |
| DELETE | `/expenses/{id}` | Delete expense   |



# Level 2 Fixes — Apply Guide

## What changed and why

| File | Change |
|---|---|
| `model/LoginRequest.java` | Already created in Level 1 — included here for completeness |
| `repository/ExpenseRepository.java` | Added `findByUserId(userId, Pageable)` for pagination |
| `service/ExpenseService.java` | Added `Logger` calls + paginated `getExpensesForUser` overload |
| `service/UserService.java` | Added `Logger` calls for register/login attempts |
| `controller/ExpenseController.java` | New `GET /api/expenses/page?page=0&size=10` endpoint + Swagger `@Operation`/`@Tag` annotations |
| `controller/AuthController.java` | Added Swagger `@Operation`/`@Tag` annotations |
| `config/OpenApiConfig.java` | **NEW FILE** — adds the "Authorize" button to Swagger UI for Bearer tokens |
| `security/SecurityConfig.java` | Permits Swagger UI / API docs paths without login |
| `security/JwtFilter.java` | Skips JWT check for Swagger paths |
| `pom.xml` | Confirms Swagger + test dependencies are present |
| `application.properties` | Logging levels + Swagger UI path config |
| `test/.../UserServiceTest.java` | **NEW FILE** — 5 unit tests for register/login logic |
| `test/.../ExpenseServiceTest.java` | **NEW FILE** — 4 unit tests for add/get/delete logic |
| `frontend/src/pages/Dashboard.js` | Now uses the paginated endpoint with Previous/Next buttons |

## How to apply (VS Code)

### Backend
1. Copy all files from `backend/src/main/java/...` into your project, overwriting existing files.
2. Copy the two new test files into `src/test/java/com/expensetracker/expense_tracker/service/`
   (create the `service` folder under `src/test/java/.../` if it doesn't exist).
3. Replace `pom.xml` and `application.properties`.

### Frontend
1. Replace `src/pages/Dashboard.js` with the one provided.

## Run order
```bash
./mvnw spring-boot:run
```

## How to test Level 2 features

### 1. Run the unit tests
```bash
./mvnw test
```
You should see output like:
```
Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### 2. View Swagger documentation
Open your browser:
```
http://localhost:8080/swagger-ui.html
```
- You'll see all endpoints listed (Authentication + Expenses)
- Click the green **Authorize** button (top right) → paste your JWT token (no need to type "Bearer", just the token) → click Authorize
- Now you can click "Try it out" on any endpoint and test it directly in the browser

### 3. Test pagination
In Thunder Client:
```
GET http://localhost:8080/api/expenses/page?page=0&size=5
Headers: Authorization: Bearer <your token>
```
Response will look like:
```json
{
  "content": [ ...up to 5 expenses... ],
  "totalElements": 12,
  "totalPages": 3,
  "number": 0,
  "size": 5
}
```

### 4. Check logs
Watch your backend terminal while using the app — you'll now see lines like:
```
INFO ... UserService : Login attempt for email=tej2@gmail.com
INFO ... UserService : Login successful for email=tej2@gmail.com
INFO ... ExpenseService : Adding expense 'Lunch' for user id=1
```

### 5. Test frontend pagination
Add 6+ expenses through the Dashboard UI — you should now see "Previous / Next" buttons appear below the expense list once you have more than 5 expenses (PAGE_SIZE = 5).



# Contribution
  
Contributions are welcome!
Fork the repository, create a branch, and submit a pull request.
     
# Author
   Email: vipintej7797@gmail.com
   GitHUb: Tejkumar1

