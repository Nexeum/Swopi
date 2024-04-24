using System.ComponentModel.DataAnnotations;

namespace order_ms.dto.response
{
    public class Order
    {
        [StringLength(30)] 
        public string OrderId { get; set; } = string.Empty;
        public decimal TotalOrderPrice { get; set; }
        public string? OrderStatus { get; set; } = string.Empty;
        public List<ProductItem>? OrderItems { get; set; } = new List<ProductItem>();
        public DateTime CreatedAt { get; set; }
        public DateTime UpdatedAt { get; set; }
    }
}