# Expense Sharing Application

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Setup](#project-setup)
- [API Endpoints](#api-endpoints)
    - [User APIs](#user-apis)
    - [Expense APIs](#expense-apis)
- [JWT Authentication](#jwt-authentication)
- [Testing the APIs](#testing-the-apis)

## Introduction

This is a **Spring Boot** application for managing and sharing daily expenses among users. It allows users to sign up, log in, add expenses, split them among participants, and download balance sheets in PDF or CSV format.

## Features

- User Registration and Login
- JWT-based Authentication
- Add expenses with equal or exact splits
- Generate balance sheets in PDF or CSV format
- Paginated view of expenses

## Technologies Used

- **Spring Boot** for backend RESTful API
- **Spring Data JPA** for database access
- **Spring Security with JWT** for authentication
- **H2 Database** (for in-memory testing, can be replaced with MySQL or PostgreSQL)
- **PDF and CSV generation** for balance sheets

## Project Setup

### 1. Clone the Repository

    ```bash
    git clone https://github.com/your-repo/expense-sharing-app.git
    cd expense-sharing-app


### 2. Build and Run the Project    
    ```bash
    ./mvnw spring-boot:run

### 3. Database Configuration 
    ```bash
    http://localhost:8080/h2-console

### 4. Postman Collection
    A Postman collection with all the API endpoints is available in the project for easy testing.


### API Endpoints

#### User APIs

#### 1.  Sign Up

##### URL: POST  http://localhost:8080/users/signup
Description: Register a new user in the system.

##### Request:

[//]: # (    ```bash )
    {
        "name": "John Doe",
        "email": "john@example.com",
        "password": "password123",
        "mobile": "1234567890"
    }

#### 2. Login

##### URL: POST  http://localhost:8080/users/login
Description: login in the system.

##### Request:

[//]: # (    ```bash )
    {
      "email": "john@example.com",
      "password": "password123"
    } 
    Response (Success 200):
    {
        "token": "eyJhbGciOiJIUzI1NiIsInR..."
    }

Copy the token and for other apis to work uset Token Bearer in header
#### *IMP Add atleat 2 users
#### 3.  Get User by ID

##### URL: Get  http://localhost:8080/users/{id}
##### Description: Get user details by user ID. Requires authentication.
##### Headers: Authorization: Bearer <JWT_TOKEN>


#### 3.  Get All Users

##### URL: Get  http://localhost:8080/users
##### Description: Get a list of all users. Requires authentication.
##### Headers: Authorization: Bearer <JWT_TOKEN>


### APIs in  Expense

#### 1. Add Expense

##### URL: POST  http://localhost:8080/expenses
Description: Add a new expense with participants and split types.

##### Request:

[//]: # (    ```bash )
    {
      "description": "Dinner",
      "totalAmount": 1000,
      "participantIds": [1, 2],
      "splits": [
        { "userId": 1, "splitType": "EQUAL" },
        { "userId": 2, "splitType": "EQUAL" }
      ]
    }

##### Response (Success 200):
    {
    "id": 3,
    "description": "Dinner",
    "totalAmount": 1000.0,
    "splits": [
        {
            "userId": 1,
            "splitType": "EqualSplit",
            "amount": 500.0
        },
        {
            "userId": 4,
            "splitType": "EqualSplit",
            "amount": 500.0
        }
      ]
    }
#### 2. Download Balance Sheet (PDF/CSV)

##### URL: Get  http://localhost:8080/expenses/{userId}/balance-sheet?format=pdf
##### Description: Download the balance sheet for a user in PDF or CSV format.
##### Query Parameters: format=pdf or format=csv

##### Response (Success 200):
The response is a downloadable file (PDF or CSV).


#### 3. Get Paginated Expenses
##### URL: Get  http://localhost:8080/expenses
##### Description: Get a paginated list of expenses.
##### Query Parameters: page=0, size=10

