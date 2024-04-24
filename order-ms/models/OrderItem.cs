using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;

namespace order_ms.models
{
    public class OrderItem
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        [StringLength(100)]
        public string Id { get; init; } = string.Empty;
        public long ProductId { get; init; }
        [StringLength(200)]
        public string? ProductName { get; init; } = string.Empty;
        public long ProductQuantity { get; init; }
        [Column(TypeName = "decimal(18, 2)")]
        public decimal TotalPrice { get; init; }
        [Column(TypeName = "decimal(18, 2)")]
        public decimal PricePerUnit { get; init; }
        [StringLength(200)]
        public string? ProductImageUrl { get; init; } = string.Empty;
        [ForeignKey("OrderId")]
        public ProductOrder? Order { get; init; }
        
        public OrderItem()
        {
            Id = ObjectId.GenerateNewId().ToString();
        }
    }
}