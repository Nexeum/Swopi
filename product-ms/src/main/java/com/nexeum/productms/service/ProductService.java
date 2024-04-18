package com.nexeum.productms.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface ProductService {
    ResponseEntity<?> addProduct(MultipartFile imageFile, String name, String description, String brandName,
                                 BigDecimal pricePerUnit, BigDecimal productWholeSalePrice, Long noOfStocks);
    ResponseEntity<?> getAllProducts();
}
