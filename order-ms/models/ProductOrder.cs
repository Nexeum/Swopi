using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace order_ms.models
{
    public class ProductOrder
    {
        
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        [StringLength(100)]
        public string Id { get; set; }
        [StringLength(200)]
        public string CartId { get; set; }
        [Column(TypeName = "decimal(18, 2)")]
        public decimal TotalOrderPrice { get; set; }
        [StringLength(25)]
        public string? OrderStatus { get; set; } = string.Empty;

        [InverseProperty("Order")]
        public List<OrderItem> OrderItems { get; set; } = new List<OrderItem>();

        public DateTime CreatedAt { get; set; }
        public DateTime UpdatedAt { get; set; }
        public byte[]? Timestamp { get; set; } = new byte[0];

        public ProductOrder()
        {
            Id = ObjectId.GenerateNewId().ToString();
            CartId = string.Empty;
            CreatedAt = DateTime.UtcNow;
        }
    }
}