namespace order_ms.dto.response
{
    public class Order
    {
        public long OrderId { get; set; }
        public decimal TotalOrderPrice { get; set; }
        public string? OrderStatus { get; set; } = string.Empty;
        public List<ProductItem>? OrderItems { get; set; } = new List<ProductItem>();
        public DateTime CreatedAt { get; set; }
        public DateTime UpdatedAt { get; set; }
    }
}