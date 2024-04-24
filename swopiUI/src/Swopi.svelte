<script>
    import {apiGateway} from './Store.js';

    let orders = [];
    let products = [];

    const unsubscribe = apiGateway.subscribe(value => {
        orders = value
    });

    let order = {
        CartId: null,
        TotalOrderPrice: null,
        OrderItems: [
            {
                ProductId: null,
                ProductName: "",
                ProductQuantity: null,
                PricePerUnit: null,
                ProductImageUrl: "",
                TotalPrice: null
            }
        ]
    };

    let product = {
        name: "",
        description: "",
        brandName: "",
        pricePerUnit: null,
        productWholeSalePrice: null,
        noOfStocks: null
    };


    apiGateway.getAllOrders();
    apiGateway.getAllProducts().then(data => products = data);

    let selectedProduct = "";

    function fillForm() {
        const product = products.find(product => product.id === selectedProduct);
        if (product) {
            order.OrderItems[0].ProductId = product.id;
            order.OrderItems[0].ProductName = product.name;
            order.OrderItems[0].PricePerUnit = product.pricePerUnit;
            calculateTotalPrice();
        }
    }

    function validateQuantity() {
        const product = products.find(product => product.id === selectedProduct);
        if (product && order.OrderItems[0].ProductQuantity > product.noOfStocks) {
            order.OrderItems[0].ProductQuantity = product.noOfStocks;
        }
        calculateTotalPrice();
    }

    function calculateTotalPrice() {
        order.OrderItems[0].TotalPrice = order.OrderItems[0].ProductQuantity * order.OrderItems[0].PricePerUnit;
    }
</script>
<section>
    <div class="container">
        <div id="liveAlertPlaceholder"></div>
        <div class="row mt-5">
            <div class="col-md-6">
                <div class="card p-2 shadow">
                    <div class="card-body">
                        <h5 class="card-title mb-4">Add New Order</h5>
                        <form>
                            <div class="form-group">
                                <label for="cartId">Cart ID</label>
                                <input bind:value={order.CartId} type="number" class="form-control" id="cartId"
                                       placeholder="Cart ID"/>
                            </div>
                            <div class="form-group">
                                <label for="totalOrderPrice">Total Order Price</label>
                                <input bind:value={order.TotalOrderPrice} type="number" class="form-control"
                                       id="totalOrderPrice" placeholder="Total Order Price"/>
                            </div>
                            <div class="form-group">
                                <label for="sproduct" style="display: block;">Select a product</label>
                                <select id="sproduct" bind:value={selectedProduct} on:change={fillForm}
                                        style="display: block; width: 100%;">
                                    <option value="">Select a product</option>
                                    {#each products as product (product.id)}
                                        <option value={product.id}>{product.name}</option>
                                    {/each}
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="productId">Product ID</label>
                                <input bind:value={order.OrderItems[0].ProductId} type="number" class="form-control"
                                       id="productId" placeholder="Product ID" disabled/>
                            </div>
                            <div class="form-group">
                                <label for="productName">Product Name</label>
                                <input bind:value={order.OrderItems[0].ProductName} type="text" class="form-control"
                                       id="productName" placeholder="Product Name" disabled/>
                            </div>
                            <div class="form-group">
                                <label for="productQuantity">Product Quantity</label>
                                <input bind:value={order.OrderItems[0].ProductQuantity} type="number"
                                       class="form-control" id="productQuantity" placeholder="Product Quantity"
                                       on:input={validateQuantity}/>
                            </div>
                            <div class="form-group">
                                <label for="pricePerUnit">Price Per Unit</label>
                                <input bind:value={order.OrderItems[0].PricePerUnit} type="number" class="form-control"
                                       id="pricePerUnit" placeholder="Price Per Unit" disabled/>
                            </div>
                            <div class="form-group">
                                <label for="productImageUrl">Product Image URL</label>
                                <input bind:value={order.OrderItems[0].ProductImageUrl} type="text" class="form-control"
                                       id="productImageUrl" placeholder="Product Image URL"/>
                            </div>
                            <div class="form-group">
                                <label for="totalPrice">Total Price</label>
                                <input bind:value={order.OrderItems[0].TotalPrice} type="number" class="form-control"
                                       id="totalPrice" placeholder="Total Price" disabled/>
                            </div>
                            <button type="submit" style="margin-top: 10px;"
                                    on:click|preventDefault={() => apiGateway.createOrder(order)}
                                    class="btn btn-success">Create Order
                            </button>
                            <button type="button" style="margin-top: 10px;" class="btn btn-primary"
                                    data-bs-toggle="modal"
                                    data-bs-target="#addProductModal">
                                Add Product
                            </button>
                        </form>

                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <h3 class="text-center">Orders:</h3>
                {#each orders as order (order.orderId)}
                    <div class="card mb-3 shadow-lg">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">Order ID: {order.orderId}</h5>
                        </div>
                        <div class="card-body">
                            <p class="card-text"><strong>Total Price:</strong> ${order.totalOrderPrice}</p>
                            <p class="card-text"><strong>Order Status:</strong> {order.orderStatus}</p>
                            <div>
                                <h6 class="text-secondary">Order Items:</h6>
                                <ul class="list-group">
                                    {#each order.orderItems as item}
                                        <li class="list-group-item">
                                            <p class="mb-0"><strong>Product Name:</strong> {item.productName}</p>
                                            <p class="mb-0"><strong>Quantity:</strong> {item.productQuantity}</p>
                                            <p class="mb-0"><strong>Total Price:</strong> ${item.totalPrice}</p>
                                        </li>
                                    {/each}
                                </ul>
                            </div>
                            <p class="card-text"><strong>Created
                                At:</strong> {new Date(order.createdAt).toLocaleString()}</p>
                            <button on:click={() => apiGateway.cancelOrder(order.orderId)} class="btn btn-danger mt-2">
                                Cancel Order
                            </button>
                        </div>
                    </div>
                {/each}
            </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addProductModalLabel">Add Product</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label for="productName">Product Name</label>
                                <input bind:value={product.name} type="text" class="form-control" id="productName"
                                       placeholder="Product Name"/>
                            </div>
                            <div class="form-group">
                                <label for="productDescription">Product Description</label>
                                <input bind:value={product.description} type="text" class="form-control"
                                       id="productDescription" placeholder="Product Description"/>
                            </div>
                            <div class="form-group">
                                <label for="brandName">Brand Name</label>
                                <input bind:value={product.brandName} type="text" class="form-control" id="brandName"
                                       placeholder="Brand Name"/>
                            </div>
                            <div class="form-group">
                                <label for="pricePerUnit">Price Per Unit</label>
                                <input bind:value={product.pricePerUnit} type="number" class="form-control"
                                       id="pricePerUnit" placeholder="Price Per Unit"/>
                            </div>
                            <div class="form-group">
                                <label for="productWholeSalePrice">Product Wholesale Price</label>
                                <input bind:value={product.productWholeSalePrice} type="number" class="form-control"
                                       id="productWholeSalePrice" placeholder="Product Wholesale Price"/>
                            </div>
                            <div class="form-group">
                                <label for="noOfStocks">Number of Stocks</label>
                                <input bind:value={product.noOfStocks} type="number" class="form-control"
                                       id="noOfStocks" placeholder="Number of Stocks"/>
                            </div>
                            <button type="submit" on:click|preventDefault={() => apiGateway.addProduct(product)}
                                    class="btn btn-primary" style="margin-top: 10px;"
                                    data-bs-dismiss="modal">Add Product
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>