package com.nexeum.orderms.service;

import com.nexeum.orderms.dto.request.CancelOrderRequest;
import com.nexeum.orderms.dto.request.CreateOrderRequest;
import com.nexeum.orderms.dto.request.GetAllOrderRequest;
import com.nexeum.orderms.dto.response.GetAllOrderResponse;
import com.nexeum.orderms.dto.response.ProductItem;
import com.nexeum.orderms.dto.response.ServiceResponse;
import com.nexeum.orderms.dto.response.Order;
import com.nexeum.orderms.entity.OrderItem;
import com.nexeum.orderms.entity.ProductOrder;
import com.nexeum.orderms.repository.ProductOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Override
    public ResponseEntity<?> createOrder(CreateOrderRequest request) {
        ResponseEntity<?> responseEntity;
        ServiceResponse serviceResponse = new ServiceResponse();

        try {
            ProductOrder productOrder = new ProductOrder();
            productOrder.setCartId(request.getCartId());
            productOrder.setOrderStatus("In Progress");

            List<OrderItem> orderItems = new ArrayList<>();

            request.getOrderItems().forEach(orderProductItem -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(orderProductItem.getProductId());
                orderItem.setProductName(orderProductItem.getProductName());
                orderItem.setProductQuantity(orderProductItem.getProductQuantity());
                orderItem.setUnitPrice(orderProductItem.getUnitPrice());
                orderItem.setTotalPrice(orderProductItem.getTotalPrice());
                orderItem.setProductImageUrl(orderProductItem.getProductImageUrl());
                orderItems.add(orderItem);
            });

            productOrder.setOrderItems(orderItems);
            productOrder.setTotalOrderPrice(request.getTotalOrderPrice());
            productOrderRepository.save(productOrder);
            serviceResponse.setCode("200");
            serviceResponse.setMessage("Order Created Successfully");
            responseEntity = new ResponseEntity<>(serviceResponse, HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setCode("500");
            serviceResponse.setMessage("Internal Server Error");
            responseEntity = new ResponseEntity<>(serviceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Override
    public ResponseEntity<?> cancelOrder(CancelOrderRequest request) {
        ResponseEntity<?> responseEntity;
        ServiceResponse serviceResponse = new ServiceResponse();

        try {
            ProductOrder productOrder = productOrderRepository.findById(request.getOrderId()).orElse(null);
            productOrder.setOrderStatus("Cancelled");
            productOrderRepository.save(productOrder);
            serviceResponse.setCode("200");
            serviceResponse.setMessage("Order Cancelled Successfully");
            responseEntity = new ResponseEntity<>(serviceResponse, HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setCode("500");
            serviceResponse.setMessage("Internal Server Error");
            responseEntity = new ResponseEntity<>(serviceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Override
    public ResponseEntity<?> getAllOrders(GetAllOrderRequest request) {
        ResponseEntity<?> responseEntity;

        try {
            List<ProductOrder> productOrders = productOrderRepository.findByCartId(request.getCartId());

            if (!(productOrders.isEmpty())) {
                GetAllOrderResponse response = new GetAllOrderResponse();

                List<Order> orders = new ArrayList<>();

                for (ProductOrder productOrder : productOrders) {
                    Order order = new Order();
                    order.setOrderId(productOrder.getId());
                    order.setTotalOrderPrice(productOrder.getTotalOrderPrice());
                    order.setOrderStatus(productOrder.getOrderStatus());
                    order.setTotalOrderPrice(productOrder.getTotalOrderPrice());
                    order.setCreatedDate(productOrder.getCreatedDate());
                    order.setUpdatedDate(productOrder.getUpdatedDate());

                    List<ProductItem> products = new ArrayList<>();

                    productOrder.getOrderItems().forEach(orderItem -> {
                        ProductItem productItem = new ProductItem();
                        productItem.setId(orderItem.getId());
                        productItem.setProductId(orderItem.getProductId());
                        productItem.setProductName(orderItem.getProductName());
                        productItem.setProductQuantity(orderItem.getProductQuantity());
                        productItem.setUnitPrice(orderItem.getUnitPrice());
                        productItem.setTotalPrice(orderItem.getTotalPrice());
                        productItem.setProductImageUrl(orderItem.getProductImageUrl());
                        products.add(productItem);
                    });

                    order.setOrderItems(products);
                    orders.add(order);
                }
                response.setOrders(orders);
                responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        }catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}
