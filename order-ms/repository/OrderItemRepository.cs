using Microsoft.EntityFrameworkCore;
using OrderMs.Models;

namespace OrderMs.Repository
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