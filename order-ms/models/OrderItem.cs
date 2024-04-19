using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace order_ms.models
{
    public class OrderItem
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; init; }
        public long ProductId { get; init; }
        [StringLength(200)]
        public string? ProductName { get; init; } = string.Empty;
        public long ProductQuantity { get; init; }
        public decimal TotalPrice { get; init; }
        public decimal PricePerUnit { get; init; }
        [StringLength(200)]
        public string? ProductImageUrl { get; init; } = string.Empty;
        [ForeignKey("OrderId")]
        public ProductOrder? Order { get; init; }
    }
}