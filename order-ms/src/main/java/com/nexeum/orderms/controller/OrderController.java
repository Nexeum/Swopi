package com.nexeum.orderms.controller;

import com.nexeum.orderms.dto.request.CancelOrderRequest;
import com.nexeum.orderms.dto.request.CreateOrderRequest;
import com.nexeum.orderms.dto.request.GetAllOrderRequest;
import com.nexeum.orderms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/get-all-orders")
    public ResponseEntity<?> getAllOrders(@RequestBody GetAllOrderRequest request) {
        ResponseEntity<?> response = orderService.getAllOrders(request);
        return response;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        ResponseEntity<?> response = orderService.createOrder(request);
        return response;
    }

    @PostMapping("/cancel-order")
    public ResponseEntity<?> cancelOrder(@RequestBody CancelOrderRequest request) {
        ResponseEntity<?> response = orderService.cancelOrder(request);
        return response;
    }
}

