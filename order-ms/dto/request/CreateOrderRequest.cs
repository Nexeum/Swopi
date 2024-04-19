using System.Collections.Generic;

namespace OrderMs.Dto.Request
{
    public class CreateOrderRequest
    {
        public long CartId { get; set; }
        public decimal TotalOrderPrice { get; set; }
        public List<OrderProductItem> OrderItems { get; set; }
    }
}