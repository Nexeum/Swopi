package com.nexeum.orderms.service;

import org.springframework.http.ResponseEntity;

public interface OrderController {
    ResponseEntity<?> createOrder();
    ResponseEntity<?> cancelOrder();
    ResponseEntity<?> getAllOrders();
}
