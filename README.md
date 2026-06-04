# User API

A Spring Boot REST API for managing users, developed following SOLID principles, layered architecture, unit testing, and Test-Driven Development (TDD).

## Project Overview

This project provides a simple User Management API that allows:

* Creating users
* Retrieving all users
* Retrieving a user by ID
* Searching users by name
* Validating unique email addresses

The application was developed progressively through three levels, introducing architecture improvements, exception handling, and unit testing.

---

# Technologies Used

* Java 25
* Spring Boot 3
* Maven
* JUnit 5
* Mockito
* MockMvc
* REST API
* SOLID Principles

---

# Project Structure

src/main/java
├── controllers
│ └── UserController
├── dto
│ └── CreateUserRequest
├── exceptions
│ ├── UserNotFoundException
│ └── EmailAlreadyExistsException
├── models
│ └── User
├── repository
│ ├── UserRepository
│ └── InMemoryUserRepository
├── service
│ ├── UserService
│ └── UserServiceImp

The application follows a layered architecture:

Controller → Service → Repository

Each layer has a single responsibility, making the code easier to maintain and test.

---

# Level 1 – Basic REST API

Implemented the first version of the User API.

## Features

### Create User

POST /users

Creates a new user.

### Get All Users

GET /users

Returns all registered users.

### Get User By ID

GET /users/{id}

Returns a specific user using its UUID.

### Search Users By Name

GET /users?name=value

Returns users whose names contain the specified text.

---

# Level 2 – Service Layer and SOLID Principles

The application was refactored to follow SOLID principles and layered architecture.

## Improvements

### Repository Layer

Created a UserRepository interface.

Implemented an in-memory repository:

* save()
* findAll()
* findById()
* searchByName()
* existsByEmail()

### Service Layer

Created a UserService interface and UserServiceImp implementation.

Business logic was moved from the controller to the service layer.

### Exception Handling

Implemented:

#### UserNotFoundException

Thrown when a user cannot be found by UUID.

#### EmailAlreadyExistsException

Thrown when attempting to create a user with an email that already exists.

### UUID Generation

User identifiers are generated inside the Service layer, keeping business logic separate from the Controller.

---

# Level 3 – Testing and Mockito

## Controller Tests

Implemented integration tests using MockMvc.

### Tested Endpoints

* GET /users
* POST /users
* GET /users/{id}
* GET /users?name=

Verified:

* HTTP status codes
* Response bodies
* JSON fields
* Error responses

## Service Tests

Implemented isolated unit tests using Mockito.

### createUser_shouldThrowExceptionWhenEmailAlreadyExists()

Verifies that:

* An exception is thrown when the email already exists.
* The repository save() method is never called.

### createUser_shouldCreateUserSuccessfulIfEmailNotExist()

Verifies that:

* The email validation succeeds.
* The user is saved correctly.
* The repository save() method is called exactly once.

---

# Running the Application

Clone the repository:

git clone <repository-url>

Navigate to the project folder:

cd userapi

Run the application:

mvn spring-boot:run

The API will start on:

http://localhost:8080

---

# Running Tests

Execute all tests:

mvn test

The project includes:

* Controller tests using MockMvc
* Service tests using Mockito

---

# API Examples

## Create User

POST /users

Request Body

{
"name": "Ada Lovelace",
"email": "[ada@example.com](mailto:ada@example.com)"
}

Response

{
"id": "generated-uuid",
"name": "Ada Lovelace",
"email": "[ada@example.com](mailto:ada@example.com)"
}

## Get All Users

GET /users

## Get User By ID

GET /users/{uuid}

## Search Users By Name

GET /users?name=ada

---

# Design Principles

This project follows several SOLID principles:

* Single Responsibility Principle (SRP)
* Dependency Inversion Principle (DIP)
* Interface Segregation through service and repository abstractions

The layered architecture improves maintainability, scalability, and testability.

# Author - Eric Tarres
