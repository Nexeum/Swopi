using MongoDB.Driver;
using order_ms.models;
using MongoDB.Bson;

namespace order_ms.repository
{
    public class ProductOrderRepository
    {
        private readonly IMongoCollection<ProductOrder> _productOrders;

        public ProductOrderRepository()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("test");

            _productOrders = database.GetCollection<ProductOrder>("ProductOrders");
        }

        public async Task<ProductOrder> FindByIdAsync(string id)
        {
            var objectId = new ObjectId(id);
            return await _productOrders.Find(po => po.Id == objectId.ToString()).FirstOrDefaultAsync();
        }

        public async Task UpdateOrderAsync(ProductOrder order)
        {
            await _productOrders.ReplaceOneAsync(po => po.Id == order.Id, order);
        }

        public async Task<List<ProductOrder>> FindByCartIdAsync(long cartId)
        {
            var cartIdString = cartId.ToString();
            return await _productOrders.Find(po => po.CartId == cartIdString).ToListAsync();
        }
        
        public async Task InsertOrderAsync(ProductOrder order)
        {
            await _productOrders.InsertOneAsync(order);
        }
        
        public async Task<List<ProductOrder>> GetAllOrdersAsync()
        {
            return await _productOrders.Find(_ => true).ToListAsync();
        }
    }
}