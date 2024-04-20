# Auth Microservice API

This is a Spring Boot microservice API for managing authentication and authorization. It provides endpoints for signing up, signing in, adding roles, and echoing the authenticated user's name.

## API Endpoints

The API provides the following endpoints:

### Sign Up

- **URL:** `/api/v1/auth/signup`
- **Method:** `POST`
- **Request Body:** JSON object with the following fields:
  - `username`: String (required)
  - `password`: String (required)
  - `confirmPassword`: String (required)
  - `email`: String (required)

### Sign In

- **URL:** `/api/v1/auth/signin`
- **Method:** `POST`
- **Request Body:** JSON object with the following fields:
  - `username`: String (required)
  - `password`: String (required)

### Add Role

- **URL:** `/api/v1/admin/add_role`
- **Method:** `POST`
- **Request Body:** JSON object with the following fields:
  - `username`: String (required)
  - `role`: String (required)

### Echo User

- **URL:** `/`
- **Method:** `GET`
- **Authorization:** User role required

## Running the Application

You can run the application from your IDE by running the `main` method in the `Application` class. The application will start on port 8082.

You can also run the application from the command line using Gradle:

```bash
./gradlew bootRun
```

## Testing API Endpoints

You can test the API using any HTTP client such as curl or Postman. Here is an example of how to test the signup endpoint using curl:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"username":"testuser", "password":"testpassword", "confirmPassword":"testpassword", "email":"test@example.com"}' http://localhost:8082/api/v1/auth/signup
```