# User Microservice API

This is a Spring Boot microservice API for managing user data. It provides endpoints for creating a user, retrieving user information, and creating or updating a user's address.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 8 or higher
- Gradle
- An IDE (like IntelliJ IDEA)

### Installing

1. Clone the repository to your local machine.
2. Open the project in your IDE.
3. Build the project using Gradle.

## API Endpoints

The API provides the following endpoints:

### Create User

- **URL:** `/api/user/create-user`
- **Method:** `POST`
- **Request Body:** JSON object with the following fields:
  - `userId`: String (required)
  - `email`: String (required)
  - `cartId`: String (required)
  - `address`: Object with the following fields:
    - `addressLine`: String
    - `city`: String
    - `pin`: String
    - `state`: String
    - `country`: String

### Get User Info

- **URL:** `/api/user/get-user-info`
- **Method:** `POST`
- **Request Body:** JSON object with the following field:
  - `userId`: String (required)

### Create or Update Address

- **URL:** `/api/user/create-update-address`
- **Method:** `POST`
- **Request Body:** JSON object with the following fields:
  - `userId`: String (required)
  - `addressLine`: String (required)
  - `city`: String (required)
  - `pin`: String (required)
  - `state`: String (required)
  - `country`: String (required)

## Running the Application

You can run the application from your IDE by running the `main` method in the `Application` class. The application will start on port 8085.

You can also run the application from the command line using Gradle:

```bash
./gradlew bootRun
```

## Testing API Endpoints

You can test the API using any HTTP client such as curl or Postman. Here is an example of how to test the create-user endpoint using curl:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"userId":"1", "email":"test@example.com", "cartId":"1", "address":{"addressLine":"123 Main St", "city":"Springfield", "pin":"12345", "state":"IL", "country":"USA"}}' http://localhost:8085/api/user/create-user
```

Replace the values in the JSON object with your own data.