using Microsoft.AspNetCore.Mvc;
using OrderMs.Dto.Request;
using OrderMs.Service;
using System.Threading.Tasks;

namespace OrderMs.Controllers
{
    [ApiController]
    [Route("api/v1/order")]
    public class OrderController : ControllerBase
    {
        private readonly IOrderService _orderService;

        public OrderController(IOrderService orderService)
        {
            _orderService = orderService;
        }

        [HttpPost("get_all_orders")]
        public async Task<IActionResult> GetAllOrders([FromBody] GetAllOrderRequest request)
        {
            return await _orderService.GetAllOrders(request);
        }

        [HttpPost("create_order")]
        public async Task<IActionResult> CreateOrder([FromBody] CreateOrderRequest request)
        {
            return await _orderService.CreateOrder(request);
        }

        [HttpPost("cancel_order")]
        public async Task<IActionResult> CancelOrder([FromBody] CancelOrderRequest request)
        {
            return await _orderService.CancelOrder(request);
        }
    }
}