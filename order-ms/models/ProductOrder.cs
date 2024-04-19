using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace OrderMs.Models
{
    public class ProductOrder
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long Id { get; set; }
        public long CartId { get; set; }
        public decimal TotalOrderPrice { get; set; }
        public string OrderStatus { get; set; }

        [InverseProperty("Order")]
        public List<OrderItem> OrderItems { get; set; } = new List<OrderItem>();

        public DateTime CreatedAt { get; set; }
        public DateTime UpdatedAt { get; set; }

        public ProductOrder()
        {
            CreatedAt = DateTime.UtcNow;
        }

        [Timestamp]
        public byte[] Timestamp { get; set; }
    }
}