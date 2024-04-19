using System;
using System.Collections.Generic;

namespace OrderMs.Dto.Response
{
    public class Order
    {
        public long OrderId { get; set; }
        public decimal TotalOrderPrice { get; set; }
        public string OrderStatus { get; set; }
        public List<ProductItem> OrderItems { get; set; }
        public DateTime CreatedAt { get; set; }
        public DateTime UpdatedAt { get; set; }
    }
}