package com.nexeum.productms.controller;

import com.nexeum.productms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web/product")
public class WebController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all-products")
    public ResponseEntity<?> getAllProducts() {
        ResponseEntity<?> response = productService.getAllProducts();
        return response;
    }
}
