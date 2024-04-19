# Order Microservice

## Requisitos

- [.NET 5.0 SDK](https://dotnet.microsoft.com/download)
- [Visual Studio 2019](https://visualstudio.microsoft.com/vs/) o [Visual Studio Code](https://code.visualstudio.com/)

## Instalación

1. Clona el repositorio en tu máquina local usando `git clone https://github.com/nexeum/swopi.git`
2. Navega hasta el directorio del proyecto usando `cd order-ms`

## Uso

1. Abre el proyecto en Visual Studio 2019 o Visual Studio Code.
2. En Visual Studio 2019, puedes ejecutar el proyecto haciendo clic en el botón "Start" o presionando F5. En Visual
   Studio Code, puedes ejecutar el proyecto usando el comando `dotnet run` en la terminal.
3. Navega a `http://localhost:5097` en tu navegador para ver la aplicación en ejecución.

## Uso de la API

A continuación se presentan ejemplos de cómo interactuar con los endpoints de la API.

### Obtener todas las órdenes

```http request
POST /api/v1/order/get_all_orders
Content-Type: application/json

{
  "CartId": "sample_cart_id"
}
```

### Crear una orden

```http request
POST /api/v1/order/create_order
Content-Type: application/json

{
  "CartId": "sample_cart_id",
  "OrderItems": [
    {
      "ProductId": "sample_product_id",
      "ProductName": "sample_product_name",
      "ProductQuantity": 1,
      "PricePerUnit": 100.0,
      "ProductImageUrl": "sample_product_image_url",
      "TotalPrice": 100.0
    }
  ],
  "TotalOrderPrice": 100.0
}
```

### Cancelar una orden

```http request

POST /api/v1/order/cancel_order
Content-Type: application/json

{
"OrderId": "sample_order_id"
}

```