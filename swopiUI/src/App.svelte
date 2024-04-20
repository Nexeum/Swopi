<script>
  export let getAllOrders;
  export let createOrder;
  export let cancelOrder;
  export let getAdminProducts;
  export let addProduct;
  export let getWebProduct;
  export let getAllWebProducts;

  getAllOrders();
  createOrder({});
  cancelOrder({});
  getAdminProducts();
  addProduct({});
  getWebProduct();
  getAllWebProducts();
</script>

<main>
	<h1>Admin Products</h1>
	<form on:submit|preventDefault={addProduct}>
		<label>
			Name:
			<input type="text" bind:value={newProduct.name} />
		</label>
		<label>
			Price:
			<input type="number" bind:value={newProduct.price} />
		</label>
		<button type="submit">Add Product</button>
	</form>

	<table>
		<thead>
		<tr>
			<th>Name</th>
			<th>Price</th>
			<th>Actions</th>
		</tr>
		</thead>
		<tbody>
		{#each products as product (product.id)}
			<tr>
				<td>{product.name}</td>
				<td>{product.price}</td>
				<td>
					<button on:click={() => cancelOrder({ id: product.id })}>Delete</button>
				</td>
			</tr>
		{/each}
		</tbody>
	</table>
</main>
