package com.nexeum.productms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nexeum.productms.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/admin/product")
public class AdminController {
    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add-product")
    public ResponseEntity<?> addProduct(@RequestParam("imageFile") MultipartFile imageFile,
                                        @RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("brandName") String brandName,
                                        @RequestParam("pricePerUnit") BigDecimal pricePerUnit,
                                        @RequestParam("productWholeSalePrice") BigDecimal productWholeSalePrice,
                                        @RequestParam("noOfStocks") Long noOfStocks) {
        return productService.addProduct(imageFile, name, description, brandName, pricePerUnit, productWholeSalePrice, noOfStocks);
    }
}