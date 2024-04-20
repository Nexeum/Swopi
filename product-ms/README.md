# Product Microservice API

This is a Spring Boot microservice API for managing product data. It provides endpoints for adding a product and retrieving all products.

## API Endpoints

The API provides the following endpoints:

### Add Product

- **URL:** `/api/v1/admin/products/add-product`
- **Method:** `POST`
- **Request Body:** Multipart form data with the following fields:
  - `imageFile`: File (required)
  - `name`: String (required)
  - `description`: String (required)
  - `brandName`: String (required)
  - `pricePerUnit`: BigDecimal (required, positive)
  - `productWholeSalePrice`: BigDecimal (required, positive)
  - `noOfStocks`: Long (required, positive)

### Get All Products

- **URL:** `/api/v1/web/product/all-products`
- **Method:** `GET`

## Running the Application

You can run the application from your IDE by running the `main` method in the `Application` class. The application will start on port 8081.

You can also run the application from the command line using Gradle:

```bash
./gradlew bootRun
```

## Testing API Endpoints

You can test the API using any HTTP client such as curl or Postman. Here is an example of how to test the add-product endpoint using curl:

```bash
curl -X POST -H "Content-Type: multipart/form-data" -F "imageFile=@path_to_image.jpg" -F "name=Product Name" -F "description=Product Description" -F "brandName=Brand Name" -F "pricePerUnit=100.00" -F "productWholeSalePrice=90.00" -F "noOfStocks=100" http://localhost:8081/api/v1/admin/products/add-product
```
