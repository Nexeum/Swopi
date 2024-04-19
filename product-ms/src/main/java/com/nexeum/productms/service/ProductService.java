package com.nexeum.productms.service;

import com.nexeum.productms.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Service
public interface ProductService {
    ResponseEntity<Object> addProduct(MultipartFile imageFile, String name, String description, String brandName,
                                       BigDecimal pricePerUnit, BigDecimal productWholeSalePrice, Long noOfStocks);
    ResponseEntity<Object> getAllProducts();
}
