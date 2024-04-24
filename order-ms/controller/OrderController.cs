using Microsoft.AspNetCore.Mvc;
using order_ms.dto.request;
using order_ms.service;

namespace order_ms.controller
{
    [ApiController]
    [Route("api/v1/order")]
    public class OrderController : ControllerBase
    {
        private readonly IOrderService _orderService;

        public OrderController(IOrderService orderService) => _orderService = orderService;

        [HttpGet("get_all_orders")]
        public async Task<IActionResult> GetAllOrders() => await _orderService.GetAllOrders();

        [HttpPost("create_order")]
        public async Task<IActionResult> CreateOrder([FromBody] CreateOrderRequest request) => await _orderService.CreateOrder(request);

        [HttpPost("cancel_order")]
        public async Task<IActionResult> CancelOrder([FromBody] CancelOrderRequest request) => await _orderService.CancelOrder(request);
    }
}