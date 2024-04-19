package com.nexeum.productms.controller;

import com.nexeum.productms.dto.response.ServiceResponse;
import com.nexeum.productms.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/admin/products")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/add-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<ServiceResponse>> addProduct(@Valid @RequestPart("imageFile") Mono<FilePart> imageFile,
                                                            @NotBlank @RequestPart("name") String name,
                                                            @NotBlank @RequestPart("description") String description,
                                                            @NotBlank @RequestPart("brandName") String brandName,
                                                            @NotNull @Positive @RequestPart("pricePerUnit") BigDecimal pricePerUnit,
                                                            @NotNull @Positive @RequestPart("productWholeSalePrice") BigDecimal productWholeSalePrice,
                                                            @NotNull @Positive @RequestPart("noOfStocks") Long noOfStocks) {
        return productService.addProduct(imageFile, name, description, brandName, pricePerUnit, productWholeSalePrice, noOfStocks)
                .flatMap(Mono::just)
                .onErrorResume(e -> {
                    log.error("Error adding product: ", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }
}