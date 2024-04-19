using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using OrderMs.Dto.Request;
using OrderMs.Dto.Response;
using OrderMs.Models;
using OrderMs.Repository;

namespace OrderMs.Service
{
    public class OrderService : IOrderService
    {
        private readonly ProductOrderRepository _productOrderRepository;

        public OrderService(ProductOrderRepository productOrderRepository)
        {
            _productOrderRepository = productOrderRepository;
        }

        public async Task<IActionResult> CreateOrder(CreateOrderRequest request)
        {
            var serviceResponse = new ServiceResponse();

            try
            {
                var productOrder = new ProductOrder
                {
                    CartId = request.CartId,
                    OrderStatus = "In progress",
                    OrderItems = request.OrderItems.Select(oi => new OrderItem
                    {
                        ProductId = oi.ProductId,
                        ProductName = oi.ProductName,
                        ProductQuantity = oi.ProductQuantity,
                        PricePerUnit = oi.PricePerUnit,
                        ProductImageUrl = oi.ProductImageUrl,
                        TotalPrice = oi.TotalPrice
                    }).ToList(),
                    TotalOrderPrice = request.TotalOrderPrice
                };

                await _productOrderRepository.ProductOrders.AddAsync(productOrder);
                await _productOrderRepository.SaveChangesAsync();

                serviceResponse.Code = "200";
                serviceResponse.Message = "Order Placed Successfully";

                return new OkObjectResult(serviceResponse);
            }
            catch (Exception)
            {
                serviceResponse.Code = "500";
                serviceResponse.Message = "Internal Server Error";

                return new OkObjectResult(serviceResponse);
            }
        }

        public async Task<IActionResult> CancelOrder(CancelOrderRequest request)
        {
            var serviceResponse = new ServiceResponse();

            try
            {
                var productOrder = await _productOrderRepository.ProductOrders.FindAsync(request.OrderId);
                productOrder.OrderStatus = "Cancelled";

                _productOrderRepository.ProductOrders.Update(productOrder);
                await _productOrderRepository.SaveChangesAsync();

                serviceResponse.Code = "200";
                serviceResponse.Message = "Order Cancelled";

                return new OkObjectResult(serviceResponse);
            }
            catch (Exception)
            {
                serviceResponse.Code = "500";
                serviceResponse.Message = "Internal Server Error";

                return new StatusCodeResult(500);
            }
        }

        public async Task<IActionResult> GetAllOrders(GetAllOrderRequest request)
        {
            try
            {
                var allOrders = await _productOrderRepository.FindByCartId(request.CartId);

                if (allOrders.Any())
                {
                    var response = new GetAllOrderResponse
                    {
                        Orders = allOrders.Select(o => new Order
                        {
                            OrderId = o.Id,
                            TotalOrderPrice = o.TotalOrderPrice,
                            OrderStatus = o.OrderStatus,
                            CreatedAt = o.CreatedAt,
                            UpdatedAt = o.UpdatedAt,
                            OrderItems = o.OrderItems.Select(oi => new ProductItem
                            {
                                Id = oi.Id,
                                ProductId = oi.ProductId,
                                ProductName = oi.ProductName,
                                ProductQuantity = oi.ProductQuantity,
                                TotalPrice = oi.TotalPrice,
                                PricePerUnit = oi.PricePerUnit,
                                ProductImageUrl = oi.ProductImageUrl
                            }).ToList()
                        }).ToList()
                    };

                    return new OkObjectResult(response);
                }

                return new OkObjectResult(new List<Order>());
            }
            catch (Exception)
            {
                return new StatusCodeResult(500);
            }
        }
    }
}