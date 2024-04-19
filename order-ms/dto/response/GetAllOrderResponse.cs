namespace order_ms.dto.response
{
    public class GetAllOrderResponse
    {
        public List<Order>? Orders { get; set; } = new List<Order>();
    }
}