# Architecture

This document outlines the EventHub project structure using a layered architecture pattern built with Spring. The application is organized into modules, features, and shared components to maintain clear separation of concerns and scalability.

## 1. Modules
Modules represent major application-level areas that contain their own workflow, controller, and service responsibilities.

### Authentication Module

#### Controller
- AuthController

#### Service
- AuthService

#### Security
- MyUserService
- SecurityContextService

#### DTO
- RegisterDTO
- LoginDTO
- AuthResponseDTO

#### Mapper
- AuthMapper

## 2. Features
Features represent domain-specific areas centered around business entities and their persistence.

### 1. User Feature

#### Entity
- User entity

#### Repository
- UserRepository

#### DTO
- UserDTO

#### Service
- UserService

### 2. Event Feature

#### Entity
- Event entity

#### Repository
- EventRepository

#### DTO
- EventDTO

#### Service
- EventService

### 3. EventRegistration Feature

#### Entity
- EventRegistration entity

#### Repository
- EventRegistrationRepository

#### DTO
- EventRegistrationDTO

#### Service
- EventRegistrationService

## 3. Shared Components
Shared components are cross-cutting classes used across the whole application and are not tied to one specific module or feature.

### Common Package

- **SecurityConfig** — Spring Security configuration and bean setup
- **GlobalExceptionHandler** — Centralized exception handling across all endpoints
- **ResourceNotFoundException** — Custom exception for resource not found scenarios
- **UserAlreadyExistException** — Custom exception for duplicate user registration
