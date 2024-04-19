using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace order_ms.models
{
    public class OrderItem
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }
        public long ProductId { get; set; }
        public string? ProductName { get; set; } = string.Empty;
        public long ProductQuantity { get; set; }
        public decimal TotalPrice { get; set; }
        public decimal PricePerUnit { get; set; }
        public string? ProductImageUrl { get; set; } = string.Empty;

        [ForeignKey("OrderId")]
        public ProductOrder Order { get; set; }
    }
}