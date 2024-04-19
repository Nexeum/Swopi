using Microsoft.EntityFrameworkCore;
using OrderMs.Models;
using System.Collections.Generic;
using System.Linq;

namespace OrderMs.Repository
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