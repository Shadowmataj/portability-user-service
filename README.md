# Users Service - Portability System

A RESTful microservice built with Spring Boot for managing customers and users in a number portability system. This service provides comprehensive customer management functionality with advanced filtering, pagination, and search capabilities.

## 🚀 Technologies

- **Java 25**
- **Spring Boot 4.0.1**
- **Spring Cloud 2025.1.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Netflix Eureka Client** (Service Discovery)
- **OpenFeign** (HTTP Client)
- **Spring AOP** (Aspect-Oriented Programming for logging)
- **Lombok** (Code generation)
- **OpenCSV** (CSV processing)
- **SpringDoc OpenAPI 3** (API Documentation)
- **Maven** (Build tool)

## 📋 Prerequisites

- Java 25 or higher
- Maven 3.6+
- PostgreSQL 12+
- An Eureka Server running (for service discovery)

## ⚙️ Configuration

### Database Configuration

The service uses PostgreSQL. Configure the database connection in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/users_db
spring.datasource.username=postgres
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
```

### Application Configuration

```properties
spring.application.name=users-service
server.port=8081

# Swagger/OpenAPI
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

## 🔧 Installation and Setup

1. **Clone the repository**
```bash
git clone <repository-url>
cd users-service
```

2. **Build the project**
```bash
./mvnw clean install
```

3. **Run the application**
```bash
./mvnw spring-boot:run
```

The service will start on `http://localhost:8081`

## 📚 API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8081/api-docs

## 🔌 API Endpoints

### Customer Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/customer/register` | Register a new customer |
| `GET` | `/api/customers/{id}` | Get customer by ID |
| `POST` | `/api/customers/by-email` | Get customer by email |
| `POST` | `/api/customers/by-phone` | Get customer by phone number |
| `POST` | `/api/customers/filter` | Filter customers with pagination |

### Example Requests

#### Register Customer
```bash
POST /api/customer/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890"
}
```

#### Filter Customers
```bash
POST /api/customers/filter?page=0&size=20&sortBy=id&sortDirection=asc
Content-Type: application/json

{
  "search": "john",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com"
}
```

#### Get Customer by Email
```bash
POST /api/customers/by-email
Content-Type: application/json

{
  "email": "john.doe@example.com"
}
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/portability/users_service/
│   │   ├── UsersServiceApplication.java    # Main application class
│   │   ├── aspect/
│   │   │   └── LoggingAspect.java         # AOP logging configuration
│   │   ├── config/
│   │   │   ├── AppConfig.java             # Application configuration
│   │   │   ├── OpenApiConfig.java         # Swagger/OpenAPI config
│   │   │   └── WebConfig.java             # Web configuration
│   │   ├── controller/
│   │   │   └── CustomerController.java    # REST API endpoints
│   │   ├── model/
│   │   │   ├── Customer.java              # Customer entity
│   │   │   ├── User.java                  # Base user entity
│   │   │   ├── dto/                       # Data Transfer Objects
│   │   │   └── enm/                       # Enumerations
│   │   ├── repo/
│   │   │   ├── CustomerRepo.java          # JPA repository
│   │   │   └── CustomerSpecification.java # Dynamic queries
│   │   └── service/
│   │       └── CustomerService.java       # Business logic
│   └── resources/
│       └── application.properties         # Configuration file
└── test/
    └── java/com/portability/users_service/
        └── UsersServiceApplicationTests.java
```

## ✨ Features

### Core Functionality
- ✅ Customer registration and management
- ✅ Advanced customer search and filtering
- ✅ Pagination and sorting support
- ✅ Multiple search criteria (ID, email, phone number)

### Technical Features
- ✅ **Service Discovery**: Integrated with Netflix Eureka for microservices architecture
- ✅ **AOP Logging**: Automatic logging of controller and service methods
- ✅ **API Documentation**: Interactive Swagger UI for API exploration
- ✅ **Data Validation**: Jakarta Validation annotations for input validation
- ✅ **Dynamic Filtering**: Specification pattern for complex queries
- ✅ **CSV Support**: OpenCSV integration for data import/export
- ✅ **Feign Clients**: Ready for inter-service communication

## 🔍 Data Model

### Customer Entity
- `id`: Long (auto-generated)
- `firstName`: String (required, max 50 chars)
- `lastName`: String (required, max 50 chars)
- `email`: String (required, unique, validated)
- `phoneNumber`: String (max 20 chars)
- `createdAt`: LocalDateTime (auto-generated)
- `updatedAt`: LocalDateTime (auto-updated)

## 🛠️ Development

### Running Tests
```bash
./mvnw test
```

### Building for Production
```bash
./mvnw clean package -DskipTests
java -jar target/users-service-0.0.1-SNAPSHOT.jar
```

## 📝 Logging

The application uses SLF4J with automatic AOP-based logging for:
- Method entry with parameters
- Method exit with return values
- Exception handling
- Execution time tracking

Logs are written to:
- Console (default)
- `logs/` directory (if configured)

## 🤝 Integration

This service is designed to work within a microservices ecosystem:
- **Service Discovery**: Registers with Eureka Server
- **API Gateway**: Can be accessed through an API Gateway
- **Other Services**: Can communicate via Feign clients

⚠️ **Important**: Create default database credentials before deploying to production!

---
