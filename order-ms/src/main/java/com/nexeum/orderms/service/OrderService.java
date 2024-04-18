package com.nexeum.orderms.service;

import com.nexeum.orderms.dto.request.CancelOrderRequest;
import com.nexeum.orderms.dto.request.CreateOrderRequest;
import com.nexeum.orderms.dto.request.GetAllOrderRequest;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> createOrder(CreateOrderRequest request);
    ResponseEntity<?> cancelOrder(CancelOrderRequest request);
    ResponseEntity<?> getAllOrders(GetAllOrderRequest request);
}
