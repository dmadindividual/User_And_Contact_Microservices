

---

# User and Contact Service Microservices

This project consists of microservices designed to manage user and contact information. The system includes a **User Service** for managing user accounts and a **Contact Service** for managing user-specific contacts. These microservices are orchestrated using **Eureka** for service discovery and **Spring Cloud Gateway** for API gateway and load balancing. 

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- **User Management**:
  - Register new users with an HTTP POST request.
  - Retrieve user details by user ID.
  - Update and delete users (future enhancements).

- **Contact Management**:
  - Create, retrieve, update, and delete contacts associated with specific users.
  - Retrieve all contacts for a user or search for contacts by name.

- **API Gateway with Load Balancing**:
  - Uses Spring Cloud Gateway to route and load-balance requests across microservices.
  - Standardized API paths for easy integration and maintenance.

- **Service Discovery with Eureka**:
  - Eureka Server for discovering and managing microservices, allowing easy scaling and dynamic service registration.

## Technologies Used

- **Java** - Main programming language.
- **Spring Boot** - Framework for creating standalone applications.
- **Spring Cloud** - Used for microservices infrastructure.
  - **Spring Cloud Eureka** - For service discovery.
  - **Spring Cloud Gateway** - For API routing and load balancing.
- **Spring Data JPA** - For data access and persistence.
- **MySQL** - Relational database used to store user and contact data.
- **Lombok** - Reduces boilerplate code for Java objects.
- **Docker** (optional) - For containerization and deploying services consistently.

## Architecture

This project follows a microservices architecture:
- **User Service**: Handles all user-related functionalities.
- **Contact Service**: Manages contacts associated with each user.
- **Eureka Server**: Manages service registration and discovery.
- **API Gateway**: Routes requests to the appropriate service.

```plaintext
                     +--------------------+
                     |   Eureka Server    |
                     +--------------------+
                              |
                              |
          +-------------------+-------------------+
          |                                       |
+---------v----------+                   +--------v---------+
|   User Service     |                   |   Contact Service|
+--------------------+                   +-------------------+
| /api/v1/users      |                   | /api/v1/contacts |
+--------------------+                   +-------------------+
                              |
                              |
                     +--------v---------+
                     |   API Gateway    |
                     +------------------+
                     |  Routes requests |
                     +------------------+
```

## Getting Started

### Prerequisites

- **Java 17 or higher**
- **MySQL Database** (or modify the configuration to use another database)
- **Maven** for building the project
- **Docker** (optional, for containerized deployment)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/your-repo.git
   cd your-repo
   ```

2. **Set up the MySQL Database**:
   - Create a MySQL database for the User Service and Contact Service.
   - Update `application.properties` in both services to reflect your database configurations.

3. **Run Eureka Server**:
   ```bash
   cd eureka-server
   mvn spring-boot:run
   ```

4. **Run User and Contact Services**:
   In separate terminals, navigate to each service directory and run:
   ```bash
   cd user_service
   mvn spring-boot:run
   ```
   ```bash
   cd contact_service
   mvn spring-boot:run
   ```

5. **Run the API Gateway**:
   ```bash
   cd api-gateway
   mvn spring-boot:run
   ```

6. Access the **Eureka Dashboard** at `http://localhost:8761` to view registered services.

### API Endpoints

The following endpoints are exposed by the API Gateway (assuming the gateway is running on `localhost:8080`):

#### User Service

- **Register User**: `POST /api/v1/users/register`
- **Get User by ID**: `GET /api/v1/users/{id}`

#### Contact Service

- **Create Contact**: `POST /api/v1/contacts/{userId}`
- **Get Contact by ID**: `GET /api/v1/contacts/{userId}/{contactId}`
- **Update Contact**: `PUT /api/v1/contacts/{userId}/{contactId}`
- **Delete Contact**: `DELETE /api/v1/contacts/{userId}/{contactId}`
- **Get All Contacts for a User**: `GET /api/v1/contacts/{userId}/all`
- **Search Contacts by Name**: `GET /api/v1/contacts/{userId}/contacts/by-name/{name}`

## Contributing

Contributions are welcome! Please fork this repository, make your changes, and submit a pull request for review.

## License

This project is licensed under the MIT License. See `LICENSE` file for details.

--- 

This `README.md` file should give you and other developers clear guidance on the project’s purpose, setup, and usage. Feel free to expand upon it based on any additional features or technologies specific to your implementation!
