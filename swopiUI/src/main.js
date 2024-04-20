import App from './App.svelte';

let orders;
let products;
let webProduct;
let allWebProducts;

async function getAllOrders() {
  const response = await fetch('http://localhost:4000/api/get_all_orders', { method: 'POST' });
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  orders = await response.json();
}

async function createOrder(orderData) {
  const response = await fetch('http://localhost:4000/api/create_order', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(orderData)
  });
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  orders = await response.json();
}

async function cancelOrder(orderData) {
  const response = await fetch('http://localhost:4000/api/cancel_order', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(orderData)
  });
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  orders = await response.json();
}

async function getAdminProducts() {
  const response = await fetch('http://localhost:4000/api/get_admin_products', { method: 'GET' });
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  products = await response.json();
}

async function addProduct(productData) {
  const response = await fetch('http://localhost:4000/api/add_product', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(productData)
  });
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  products = await response.json();
}

async function getWebProduct() {
  const response = await fetch('http://localhost:4000/api/get_web_product', { method: 'GET' });
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  webProduct = await response.json();
}

async function getAllWebProducts() {
  const response = await fetch('http://localhost:4000/api/get_all_web_products', { method: 'GET' });
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  allWebProducts = await response.json();
}

const app = new App({
  target: document.body,
  props: {
    name: 'world',
    getAllOrders,
    orders,
    products,
    webProduct,
    allWebProducts
  }
});

export default app;