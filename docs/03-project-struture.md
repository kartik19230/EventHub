# Project Structure

EventHub follow feature-based package structure. Instead of organizing the application by technical layers (controller ,services , repositories), related component are grouped by business feature.

This organization improves modularity, scalability, and maintainability as the application grows.

---

### Root Package

>com.eventhub

The root packages contain all application modules and shared infrastructure.

---

### auth

Responsible for authentication and authorization infrastructure.

Current Responsibilities include:
* User Registration
* User Login
* Email Verification
* JWT Authentication
* Security Configuration
* Authentication DTOs
* Authentication events
* Email Verification Tokens
* Authentication-related exception
* Authentication mappers

This module own everything required to authenticate users and manage account activation.

---
 
### user

Responsible for user domain management.

Current responsibilities include: 
* User entity
* User repository
* User roles
* User-related enums

Authentication logic is intentionally separated from the user domain. The user module focuses only on user persistence and domain modeling.

---

### event 

Responsible for Event management.

Planned responsibilities include:
* Event creation
* Event updates
* Event deletes
* Event search 
* Event categories 
* Event ownership

This module will become the primary business domain of the application.

---

### registration

Responsible for Event Registration work flow.

Planned responsibilities include:

* Event registration
* Registration validation
* Capacity management
* Registration status
* Attendance tracking

This module will coordinate the relationship between users and events.

### common

Contains reusable components shared across multiple modules.

Examples include:

* Global exception handling
* Common exceptions
* Shared utilities
* Generic responses
* Reusable validation
* Contains application-wide configuration like Spring Configuration, Security Configuration, Configuration Properties

Only components that are genuinely shared between multiple modules should be placed here.

 ---

### Design Philosophy 

Package placement follows these principles:

* Features own their implementation.
* Shared components belong in the common module only when they are used by   multiple features.
* New business functionality should be added to an existing feature module whenever possible.
* New top-level modules should only be introduced when they represent a distinct business capability.  

 This structure keeps related code together while preventing the project from becoming difficult to navigate as additional modules are introduced.