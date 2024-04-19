namespace order_ms.dto.request
{
    public class CreateOrderRequest
    {
        public long CartId { get; set; }
        public decimal TotalOrderPrice { get; set; }
        public List<OrderProductItem>? OrderItems { get; set; } = new List<OrderProductItem>();
    }
}