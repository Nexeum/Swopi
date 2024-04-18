package com.nexeum.productms.controller;

import jakarta.validation.Valid;
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
    public ResponseEntity<?> addProduct(@Valid @RequestParam("imageFile") MultipartFile imageFile,
                                        @Valid @RequestParam("name") String name,
                                        @Valid @RequestParam("description") String description,
                                        @Valid @RequestParam("brandName") String brandName,
                                        @Valid @RequestParam("pricePerUnit") BigDecimal pricePerUnit,
                                        @Valid @RequestParam("productWholeSalePrice") BigDecimal productWholeSalePrice,
                                        @Valid @RequestParam("noOfStocks") Long noOfStocks) {
        return productService.addProduct(imageFile, name, description, brandName, pricePerUnit, productWholeSalePrice, noOfStocks);
    }
}