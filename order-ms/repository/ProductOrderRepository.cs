using Microsoft.EntityFrameworkCore;
using order_ms.models;

namespace order_ms.repository
{
    public class ProductOrderRepository : DbContext
    {
        public DbSet<ProductOrder> ProductOrders { get; set; }

        public ProductOrderRepository(DbContextOptions<ProductOrderRepository> options)
            : base(options)
        {
        }

        public List<ProductOrder> FindByCartId(long cartId)
        {
            return ProductOrders.Where(po => po.CartId == cartId).ToList();
        }
    }
}