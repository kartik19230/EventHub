# Event API Design

## Version 

Current Version: **v1**

> This document defines the API design principle, conventions, and standards followed throughout the Eventhub backend.

---

## Base URL

Current (Development)

```text 
http://localhost:8080/api/v1
```
All APIs must be versioned

Example:

```text
/api/v1/auth/login
/api/v1/auth/register
/ap1/v1/auth/registration
```

---

## General Design Principles

The EventHub API follows RESTful principles.

Guidlines:
* Use nouns instead of verbs where appropriate.
* Use HTTP methods to describe actions.
* Never expose JPA entities directly.
* Always use DTO's for requests and responses.
* Validate all the incoming HTTP request using Bean Validation.
* Return meaningful HTTP status codes.
* Keep controllers thin.
* Business login belongs in service
* Object mapping belong to mapstruct

---

## Authentciation APIs

Base Path 

```text
/api/v1/auth
```

Current Endpoints

| Method | Endpoint | Description               |
|--------|----------|---------------------------|
| POST   |/register | Register a new user       |
| POST   |/login    | Authenticate user         |
| GET    |/me       | To check session user     |

---

## Authentication Strategy

Current

* Spring Security and BCrypt Password Encoder
* Session based authentication
* HttpSession 
* SecurityContext persistence

Planned

* Session based authentication -> JWT Authentication(Stateless Authentication)

---

# Request Format

All request bodies must use JSON. 

Example

```json
{
  "email": "user@example.com",
  "password": "Password@123"
}
```

---

# Response Format

Every API returns a DTO.

Entities must never be returned directly.

Current authentication responses use:

```json
{
  "message": "Successfully Logged In"
}
```

Response DTOs may evolve as new business requirements arise.

---

# HTTP Status Code Guidelines

## Success

| Status         | Usage                                     |
| -------------- | ----------------------------------------- |
| 200 OK         | Successful retrieval or login             |
| 201 Created    | Successful resource creation              |
| 204 No Content | Successful deletion without response body |

---

## Client Errors

| Status           | Usage                                           |
| ---------------- | ----------------------------------------------- |
| 400 Bad Request  | Validation failure                              |
| 401 Unauthorized | Authentication failed                           |
| 403 Forbidden    | Authenticated but lacks permission              |
| 404 Not Found    | Requested resource does not exist               |
| 409 Conflict     | Duplicate resource (e.g., email already exists) |

---

## Server Errors

| Status                    | Usage                        |
| ------------------------- | ---------------------------- |
| 500 Internal Server Error | Unexpected application error |

---

# Validation

All request DTOs must use Jakarta Bean Validation.

Examples:

* @NotBlank
* @Email
* @Size

Validation failures return HTTP 400.

---

# Error Handling

Global exception handling is centralized using:

```text
@RestControllerAdvice
```

Custom Exception: 
* UserAlreadyExistException
* ResourceNotFoundException

Controllers should not manually handle exceptions.

Business exceptions must be translated into meaningful HTTP responses.

---

# DTO Design Principles

Use separate DTOs for:

* Requests
* Responses

Do not expose:

* Password hashes
* Internal identifiers unless required
* Internal application state

---

# Naming Conventions

Endpoints should use lowercase.

Examples

```text
/auth/login
/events
/users
```

---

# URL Design

Collections

```text
/events
/users
/tickets
```

Single Resource

```text
/events/{eventId}
```

Nested Resources (when appropriate)

```text
/events/{eventId}/registrations
```

---

# API Versioning

All public endpoints must include a version.

Example

```text
/api/v1/events
```

Future versions should introduce:

```text
/api/v2/events
```

without breaking existing clients.

---

# Authorization Strategy

Role-Based Access Control (RBAC) will be introduced after JWT integration.

Roles

* ATTENDEE
* ORGANIZER
* ADMIN

Examples

ATTENDEE

* View Events
* Register for Events

ORGANIZER

* Create Event
* Update Event
* Delete Event

ADMIN

* Manage Users
* Manage Organizers
* System Administration

---

# Pagination Standard

Future collection endpoints should support:

```text
?page=0
&size=10
&sort=createdAt,desc
```

Example

```text
GET /events?page=0&size=10
```

---

# Documentation Strategy

The project uses two complementary forms of documentation.

## API Design

This document defines architectural decisions, standards, and conventions.

## Swagger / OpenAPI

Swagger will provide automatically generated endpoint documentation, request/response schemas, and interactive API testing.

Swagger should always reflect the current implementation, while this document explains the reasoning behind the design.

---

# Development Principles

Every new API should satisfy the following:

* RESTful design
* Feature-based architecture
* DTO-based communication
* Bean Validation
* Global Exception Handling
* Consistent HTTP status codes
* Consistent naming conventions
* Maintainable service layer
* Clean separation of responsibilities

These principles apply across every module of EventHub, including Authentication, Events, Registrations, Tickets, and Payments.
