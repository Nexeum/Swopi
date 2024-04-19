using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using OrderMs.Dto.Request;

namespace OrderMs.Service
{
    public interface IOrderService
    {
        Task<IActionResult> CreateOrder(CreateOrderRequest request);

        Task<IActionResult> CancelOrder(CancelOrderRequest request);

        Task<IActionResult> GetAllOrders(GetAllOrderRequest request);
    }
}