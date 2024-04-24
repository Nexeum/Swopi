package com.nexeum.productms.controller;

import com.nexeum.productms.dto.response.ServiceResponse;
import com.nexeum.productms.entity.Product;
import com.nexeum.productms.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/v1/admin/products")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/add-product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ServiceResponse>> addProduct(@Valid @RequestBody Product productRequest) {
        return productService.addProduct(productRequest.getName(), productRequest.getDescription(), productRequest.getBrandName(), productRequest.getPricePerUnit(), productRequest.getProductWholeSalePrice(), productRequest.getNoOfStocks())
                .flatMap(Mono::just)
                .onErrorResume(e -> {
                    log.error("Error adding product: ", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ServiceResponse("500", "Internal server error")));
                });
    }
}