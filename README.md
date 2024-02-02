# Product Application

This Spring Boot project is designed for managing product data through RESTful APIs. It incorporates various components for handling authentication, authorization, asynchronous messaging using RabbitMQ, and more.

## Prerequisites

Ensure the following tools are installed before working with the project:

- IntelliJ IDEA (Community Edition)
- Java Development Kit (JDK)
- Maven
- RabbitMQ (if using RabbitMQ for messaging)
- MySQL Workbench
- Postman
- FakeSMTP Server (Configure email settings in application.properties)

## Getting Started

### Import Project

1. Open IntelliJ IDEA.
2. Click on `File` > `Open...`.
3. Navigate to the directory where your Maven project is located.
4. Select the `pom.xml` file in your project directory.
5. Click `Open`.
6. Click `OK`.

### Database Configuration (MySQL Workbench)

1. Install and open MySQL Workbench.
2. Create a new database.
3. Configure database connection details in application.properties.

### Run the Application

To run the project:

1. Launch IntelliJ IDEA on your computer.
2. Navigate to the project directory.
3. Select Main Class `ProductApplication`.
4. Select `Run 'ProductApplication'`.
5. Wait for Build and Launch.

### Swagger API Documentation

Access Swagger API documentation once the application is running:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Postman

Use Postman to test the RESTful APIs. Import the provided Postman collection with sample requests for each API endpoint.

## Configuration

The project uses application.properties for configuration. Customize the properties as needed for your environment.

## Controller Package

### ProductController

REST controller handling HTTP requests related to product management.

- **GET /products**
    - Description: Get a list of all products.
    - Authorization: Requires ROLE_ADMIN.
    - Response: List of ProductDTO objects.
- **GET /products/{id}**
    - Description: Get a product by ID.
    - Authorization: Requires ROLE_A.
    - Path Variable: id - ID of the product.
    - Response: ProductDTO or HTTP 404 if not found.
- **POST /products**
    - Description: Create a new product.
    - Authorization: Requires ROLE_ADMIN.
    - Request Body: ProductDTO object.
    - Response: Created ProductDTO.
- **DELETE /products/{id}**
    - Description: Delete a product by ID.
    - Authorization: Requires ROLE_ADMIN.
    - Path Variable: id - ID of the product.
    - Response: HTTP 204 No Content.
- **POST /products/rabbitmqpost**
    - Description: Save a new product and send details to RabbitMQ queue.
    - Request Body: ProductDTO object.
    - Response: HTTP 201 Created.

### HealthcheckController

- **GET /actuator/health**
    - Description: Health check endpoint.
    - Response: Current health status based on customHealthIndicator.

### UserController

- **POST /auth/new**
    - Description: Add a new user.
    - Request Body: UserInfo object.
    - Response: Message indicating successful user addition.
- **POST /auth/authenticate**
    - Description: Authenticate and get JWT token.
    - Request Body: AuthRequest object.
    - Response: JWT token or exception for invalid authentication.

## DTO Package

### ProductDTO

Data Transfer Object for product entities.

## Filter Package

### JwtAuthFilter

Custom filter for JWT authentication.

## Listener Package

### RabbitMqListener

Listener for RabbitMQ messages, processing, and saving product details.

## Model Package

- **AuthRequest**
    - Model class for authentication request.
- **Product**
    - JPA entity representing the "Product" table in the database.
- **UserInfo**
    - JPA entity representing the "UserInfo" table in the database.
- **UserInfoUserDetails**
    - UserDetails implementation for user authentication and authorization.

## Repository Package

- **ProductRepository**
    - JPA repository for managing Product entities.
- **UserInfoRepository**
    - JPA repository for managing UserInfo entities.

## Service Package

- **ProductService**
    - Service class for business logic related to product management.
- **JwtService**
    - Service class for JWT token generation, validation, and extraction.
- **UserInfoUserDetailsService**
    - Implementation of Spring Security's UserDetailsService for UserInfo.
- **UserService**
    - Service class for managing user-related operations.

## Configuration

The application is configured using Spring's application.properties file, along with environment-specific configuration files like application-dev1.properties, application-dev2.properties, and application-prod.properties. Additionally, there's a Logback XML configuration file for logging setup.

## Running the Application

To run the application, execute the following Maven command:

```bash
./mvn spring-boot:run -DskipdTests -Dspring-boot.run.profiles=ecommerce_db