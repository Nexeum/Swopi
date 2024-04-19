using Microsoft.EntityFrameworkCore;
using order_ms.models;
namespace order_ms.repository
{
    public class OrderItemRepository : DbContext
    {
        public DbSet<OrderItem> OrderItems { get; set; }

        public OrderItemRepository(DbContextOptions<OrderItemRepository> options)
            : base(options)
        {
        }
    }
}