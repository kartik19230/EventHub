# Architecture Principles

This document define architecture principles followed through the EventHub Project. These principles guides the development, decisions, code organization, and future developments of the application.

---

### 1. Feature-Based Architecure

The project is organized by the Business feature rather than technical layer.

**auth/**\
**user/**\
**event/**\
**registration/**\
**common/**

Each classes has it own Controller, Services, DTO's, Repositories, and supporting classes whenever appropriate. This approach improves modularity and allows features to evolve independently as the application grows.

---

### 2. Clear Separation of Roles

Each layer has a single,well-defined responsbility.
* Controller handles HTTP requests and responses.
* Service orchestrates business flows.
* Dedicated services contain domain-specific business logic.
* Repositories are only responsible for data access.
* Mappers transform objects between DTO's and domain models.

Business logic should never leak into controllers or repositories.

---

### 3. DTOs at API Boundaries

Entites are never expose through REST APIs.
 
All incoming request and outgoing responses use DTOs to:
* Protect domain models.
* Control exposed data.
* Decouple API contracts from persistence models.
* Support future API Evolution.

---

### 4. Thin Controllers

Controller should remain lightweight.

Their responsibilities are limited to:
* Request validation.
* Delegating work to services.
* Return HTTP responses.

Controller should not contain any business logic.

---

### 5. Service Coordinate workflows

Application services coordinate mulitple components to complete business use case.

Examples include:
* User registration.
* Login.
* Email Verification.
* Event creation.

Complex workflows should be orchestrated within services while delegating specialized responsibilites to dedicated components.

---

### 6. Dedicated Component for Dedicated Responsibilities

Specialized concern should be isolated into dedicated components.

Examples include:
* JwtService for token management.
* Email Service for email delivery.
* MapStruct mappers for object mapping.
* Event Listeners for asynchronous workflow.

This improves readability, testability, and maintainability.

---

### 7. Event-Driven Workflows

Operations that should occur only after succesful database transactions are implemented using Spring's Event mechanism. Transactional events improver reliability by ensuring side effect(such as email delivery) occur only after successful transaction commits.

---

### 8. Production-Oriented Security 

Security decisions prioritize maintainability and real-world practices.

Current Principles include:
* BCrypt password hashing.
* Jwt-based stateless authentication.
* Email Verification before account activation.
* Spring security activation.
* Configurational externalization using @ConfigurationProperties

---

### 9. Incremental Architecture

The project intentionally avoids unnecessary complexity. New technologies and design patterns are introduced only when they solve an existing problem or provide measurable architectural value.

---

### 10. Documentation as Part of Development  

Documentation is treated as part of the project rather than an afterthought.
Architecture decisions, module designs, and implementation details are documented alongside the codebase to improve maintainability and onboarding.
