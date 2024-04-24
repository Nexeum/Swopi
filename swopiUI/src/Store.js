import {writable} from 'svelte/store';

const store = () => {
    const state = []
    const {subscribe, set, update} = writable(state);
    const methods = {
        createOrder(order) {
            console.log(order);
            fetch('http://localhost:5097/api/v1/order/create_order', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(order)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.code === "200") {
                        console.log(data);
                        this.getAllOrders();
                    }
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        },
        getAllOrders() {
            fetch('http://localhost:5097/api/v1/order/get_all_orders', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then(data => {
                    set(data.orders);
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        },
        // src/Store.js
        cancelOrder(orderId) {
            fetch('http://localhost:5097/api/v1/order/cancel_order', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({orderId: orderId})
            })
                .then(response => response.json())
                .then(data => {
                    if (data.code === "200") {
                        console.log(data.message);
                        this.getAllOrders();
                    }
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        },
        // src/Store.js
        addProduct(product) {
            fetch('http://localhost:8081/api/v1/admin/products/add-product', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(product)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.code === "200") {
                        console.log(data.response);
                        this.showAlert('Product added successfully', 'success');
                    }
                })
                .catch((error) => {
                    console.error('Error:', error);
                    this.showAlert('Error adding product', 'danger');
                });
        },
        showAlert(message, type) {
            const placeholder = document.getElementById('liveAlertPlaceholder');
            placeholder.innerHTML = `
            <div class="alert alert-${type} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>`;
        },
        getAllProducts() {
            return fetch('http://localhost:8081/api/v1/web/product/all-products', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .catch((error) => {
                    console.error('Error:', error);
                });
        }
    }

    return {
        subscribe,
        set,
        update,
        ...methods
    }
}

export const apiGateway = store();