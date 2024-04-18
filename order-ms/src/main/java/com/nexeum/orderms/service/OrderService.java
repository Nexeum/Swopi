package com.nexeum.orderms.service;

import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> createOrder();
    ResponseEntity<?> cancelOrder();
    ResponseEntity<?> getAllOrders();
}
