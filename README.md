# Portfolio Management Platform — Spring Boot Advisory Service

A Spring Boot REST API for managing advisory sessions and developer availability within a distributed portfolio and consulting platform.

This service is part of an academic full-stack system composed of multiple independent backend applications consumed by an Angular frontend. Each backend is implemented with a different technology stack and is responsible for a specific group of business entities.

## Overview

The complete platform allows users to discover developers, review portfolios, check availability, and schedule advisory sessions.

The system is distributed across three independent REST services:

| Service | Technology | Main Responsibilities | Default Port |
|---|---|---|---|
| Developer and Project Service | Jakarta EE | Developers and portfolio projects | `8080` |
| Advisory Service | Spring Boot | Advisory sessions and availability | `8081` |
| User Service | FastAPI | Users and role-related data | `8002` |

This repository contains the **Spring Boot Advisory Service**.

## Main Responsibilities

The service manages the scheduling workflows required by the platform, including:

- Advisory-session requests
- Client contact information
- Requested dates and times
- Project descriptions
- Advisory status updates
- Response messages
- Developer availability
- Recurring availability
- One-time availability
- Schedule blocking
- Virtual and in-person modalities
- Logical association with developer profiles

## Technology Stack

| Category | Technology |
|---|---|
| Language | Java 17+ |
| Framework | Spring Boot |
| REST API | Spring Web |
| Persistence | Spring Data JPA |
| ORM | Hibernate |
| Database | PostgreSQL |
| Build tool | Maven |
| API style | REST |
| Default port | `8081` |

## Project Structure

```text
src/
└── main/
    ├── java/
    │   └── ec/edu/ups/ppw/GestorProyectos/
    │       ├── DAO/
    │       ├── bussines/
    │       ├── modelo/
    │       ├── services/
    │       └── GestionProyectosSpringApplication.java
    └── resources/
        └── application.properties
```

### Layer Responsibilities

- `DAO`: Spring Data JPA repositories
- `bussines`: application and business logic
- `modelo`: JPA entity classes
- `services`: REST controllers
- `GestionProyectosSpringApplication.java`: Spring Boot entry point
- `application.properties`: application, database, JPA, and server configuration

> The package name `bussines` is preserved because it is currently used by the source code. Renaming it to `business` would improve consistency in a future refactor.

## Managed Entities

### Advisory Session

Represents an advisory request submitted by a client to a developer.

Typical fields include:

- Client name
- Client email
- Requested date
- Requested time
- Project description
- Request status
- Response message
- Developer identifier

### Availability

Represents the periods in which a developer is available or unavailable for advisory sessions.

Typical fields include:

- Availability type
- Modality
- Day of the week
- Specific date
- Start time
- End time
- Developer identifier

Supported availability concepts may include:

- Recurring schedules
- One-time schedules
- Blocked periods
- Virtual sessions
- In-person sessions

## API Base URL

The service runs locally at:

```text
http://localhost:8081
```

## API Endpoints

### Advisory Sessions

Base path:

```text
/api/asesorias
```

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/asesorias` | Retrieve all advisory sessions |
| `GET` | `/api/asesorias/{id}` | Retrieve an advisory session by identifier |
| `POST` | `/api/asesorias` | Create an advisory-session request |
| `PUT` | `/api/asesorias/{id}` | Update an advisory session |
| `DELETE` | `/api/asesorias/{id}` | Delete an advisory session |

### Availability

Base path:

```text
/api/disponibilidades
```

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/disponibilidades` | Retrieve all availability records |
| `GET` | `/api/disponibilidades/{id}` | Retrieve an availability record |
| `POST` | `/api/disponibilidades` | Create an availability record |
| `PUT` | `/api/disponibilidades/{id}` | Update an availability record |
| `DELETE` | `/api/disponibilidades/{id}` | Delete an availability record |

## Requirements

Before running the service, install and configure:

- JDK 17 or later
- Maven
- PostgreSQL
- A PostgreSQL database compatible with the complete platform
- The related Angular frontend or another HTTP client for testing

## Installation

1. Clone the repository:

```bash
git clone https://github.com/CarlosMoyanoG/BackEnd-GestionPortafolios-Sping.git
cd BackEnd-GestionPortafolios-Sping
```

2. Verify the Java and Maven installations:

```bash
java -version
mvn -version
```

3. Configure the database environment variables.

4. Start the service:

```bash
mvn spring-boot:run
```

The API will be available at:

```text
http://localhost:8081
```

## Running from an IDE

Open the project in an IDE with Spring Boot support and run:

```text
GestionProyectosSpringApplication.java
```

Recommended IDEs include:

- IntelliJ IDEA
- Spring Tool Suite
- Visual Studio Code with Java extensions
- Apache NetBeans

## Configuration

Database credentials should not be stored directly in `application.properties`.

Use environment variables instead:

```text
DB_URL=jdbc:postgresql://localhost:5432/your_database
DB_USERNAME=your_database_user
DB_PASSWORD=your_database_password
```

A recommended `application.properties` configuration is:

```properties
spring.application.name=GestionProyectos_Spring

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

server.port=${SERVER_PORT:8081}
```

Example for PowerShell:

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/your_database"
$env:DB_USERNAME = "your_database_user"
$env:DB_PASSWORD = "your_database_password"
$env:SERVER_PORT = "8081"
```

Example for Linux or macOS:

```bash
export DB_URL="jdbc:postgresql://localhost:5432/your_database"
export DB_USERNAME="your_database_user"
export DB_PASSWORD="your_database_password"
export SERVER_PORT="8081"
```

Do not commit real database users, passwords, private keys, or environment-specific configuration files.

## Environment Template

A public `.env.example` file may contain placeholder values:

```dotenv
DB_URL=jdbc:postgresql://localhost:5432/your_database
DB_USERNAME=your_database_user
DB_PASSWORD=your_database_password
SERVER_PORT=8081
```

Add private environment files and generated output to `.gitignore`:

```gitignore
.env
.env.*
!.env.example
target/
.idea/
.vscode/
*.log
```

Spring Boot does not load `.env` files automatically. These values must be exported by the operating system, supplied by the IDE, or loaded through an additional configuration mechanism.

## Building the Application

Generate the application package with:

```bash
mvn clean package
```

The resulting JAR file will be available in:

```text
target/
```

Run the packaged application with:

```bash
java -jar target/<generated-file>.jar
```

## Example Requests

Retrieve all advisory sessions:

```bash
curl http://localhost:8081/api/asesorias
```

Retrieve all availability records:

```bash
curl http://localhost:8081/api/disponibilidades
```

Create an advisory session:

```bash
curl -X POST http://localhost:8081/api/asesorias \
  -H "Content-Type: application/json" \
  -d '{
    "nombreCliente": "Example Client",
    "correoCliente": "client@example.com",
    "descripcionProyecto": "Technical advisory request"
  }'
```

The exact payload depends on the entity fields and validation rules implemented in the source code.

## Integration with the Complete Platform

This service works together with:

- An Angular frontend
- A Jakarta EE backend for developers and projects
- A FastAPI backend for users
- A shared PostgreSQL database

Related repositories:

- [Angular Frontend](https://github.com/CarlosMoyanoG/FrontEnd-GestionPortafolios)
- [Spring Boot Backend](https://github.com/CarlosMoyanoG/BackEnd-GestionPortafolios-Sping)
- [Jakarta EE Backend](https://github.com/CarlosMoyanoG/BackEnd-GestionPortafolios-Jakarta)
- [FastAPI Backend](https://github.com/CarlosMoyanoG/BackEnd-GestionPortafolios-FastApi)

## Testing

The endpoints can be tested with:

- Postman
- Insomnia
- `curl`
- The Angular frontend
- Spring Boot integration tests

Example:

```text
GET http://localhost:8081/api/asesorias
```

Recommended automated-testing tools include:

- JUnit 5
- Spring Boot Test
- MockMvc
- Testcontainers
- Mockito

## Current Limitations

- Database creation and seed scripts are not included.
- Database migrations are not documented.
- Automated tests may not cover the complete business workflow.
- API documentation with OpenAPI or Swagger is not currently described.
- Authentication and authorisation are not documented for this service.
- Validation and error responses may require further standardisation.
- The service shares a database with other backend implementations, which creates coupling between services.
- `spring.jpa.hibernate.ddl-auto=update` is suitable for development but should not be relied on for controlled production migrations.
- Some package and entity names remain in Spanish while the public documentation is in English.

## Future Improvements

Recommended improvements include:

- Add OpenAPI and Swagger UI
- Add Flyway or Liquibase migrations
- Add PostgreSQL schema and seed scripts
- Add DTOs for request and response contracts
- Add centralised exception handling
- Add Bean Validation annotations
- Add pagination, filtering, and sorting
- Add conflict detection for overlapping schedules
- Add time-zone-aware date and time handling
- Add authentication and role-based authorisation
- Add unit and integration tests
- Add Docker support
- Add health-check and readiness endpoints
- Add structured logging
- Add CI/CD validation
- Add development and production profiles

## Academic Purpose

This service was developed to demonstrate:

- REST API development with Spring Boot
- Persistence with Spring Data JPA
- PostgreSQL integration
- Layered backend architecture
- Advisory and availability modelling
- Angular-to-API communication
- Interoperability between independent backend technologies
- Comparison between Spring Boot, Jakarta EE, and FastAPI

## Author

[**Carlos Moyano Guevara**](https://github.com/CarlosMoyanoG)

Computer Science Engineering student at Universidad Politécnica Salesiana.

## Project Status

Academic project maintained as part of a software development portfolio.

## License

No open-source licence has been added yet. Unless a licence is explicitly included, the source code may be viewed for educational and portfolio purposes but should not be copied, modified, distributed, or used commercially without permission.
