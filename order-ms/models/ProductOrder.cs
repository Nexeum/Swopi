using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace order_ms.models
{
    public class ProductOrder
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }
        public long CartId { get; set; }
        public decimal TotalOrderPrice { get; set; }
        public string? OrderStatus { get; set; } = string.Empty;

        [InverseProperty("Order")]
        public List<OrderItem> OrderItems { get; set; } = new List<OrderItem>();

        public DateTime CreatedAt { get; set; }
        public DateTime UpdatedAt { get; set; }
        public byte[]? Timestamp { get; set; } = new byte[0];

        public ProductOrder()
        {
            CreatedAt = DateTime.UtcNow;
        }
    }
}