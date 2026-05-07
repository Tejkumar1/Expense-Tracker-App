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


# Contribution
  
Contributions are welcome!
Fork the repository, create a branch, and submit a pull request.
     
# Author
   Email: vipintej7797@gmail.com
   GitHUb: Tejkumar1

