using Microsoft.AspNetCore.Mvc;
using order_ms.dto.request;

namespace order_ms.service
{
    public interface IOrderService
    {
        Task<IActionResult> CreateOrder(CreateOrderRequest request);

        Task<IActionResult> CancelOrder(CancelOrderRequest request);

        Task<IActionResult> GetAllOrders();
    }
}
