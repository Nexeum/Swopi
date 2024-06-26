package com.nexeum.productms.service;

import com.nexeum.productms.dto.response.ServiceResponse;
import com.nexeum.productms.entity.Product;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


public interface ProductService {
    Mono<ResponseEntity<ServiceResponse>> addProduct(String name, String description, String brandName,
                                                     BigDecimal pricePerUnit, BigDecimal productWholeSalePrice, Long noOfStocks);
    Flux<Product> getAllProducts();
}