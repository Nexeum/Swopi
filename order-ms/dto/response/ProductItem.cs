using System;

namespace order_ms.dto.response
{
    public class ProductItem
    {
        public long Id { get; set; }
        public long ProductId { get; set; }
        public string? ProductName { get; set; } = string.Empty;
        public long ProductQuantity { get; set; }
        public decimal TotalPrice { get; set; }
        public decimal PricePerUnit { get; set; }
        public string? ProductImageUrl { get; set; } = string.Empty;
    }
}