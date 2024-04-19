namespace order_ms.dto.request
{
    public class OrderProductItem
    {
        public long ProductId { get; set; }
        public string? ProductName { get; set; } = string.Empty;
        public long ProductQuantity { get; set; }
        public decimal TotalPrice { get; set; }
        public decimal PricePerUnit { get; set; }
        public string? ProductImageUrl { get; set; } = string.Empty;
    }
}