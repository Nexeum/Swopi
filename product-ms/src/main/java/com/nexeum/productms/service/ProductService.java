package com.nexeum.productms.service;

import com.nexeum.productms.dto.response.ServiceResponse;
import com.nexeum.productms.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


@Service
public interface ProductService {
    Mono<ResponseEntity<ServiceResponse>> addProduct(Mono<FilePart> imageFile, String name, String description, String brandName,
                                                     BigDecimal pricePerUnit, BigDecimal productWholeSalePrice, Long noOfStocks);
    Flux<Product> getAllProducts();
}