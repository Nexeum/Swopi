using System;

namespace OrderMs.Dto.Request
{
    public class OrderProductItem
    {
        public long ProductId { get; set; }
        public string ProductName { get; set; }
        public long ProductQuantity { get; set; }
        public decimal TotalPrice { get; set; }
        public decimal PricePerUnit { get; set; }
        public string ProductImageUrl { get; set; }
    }
}