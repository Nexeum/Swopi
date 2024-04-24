package com.nexeum.productms.service;

import com.nexeum.productms.dto.response.ServiceResponse;
import com.nexeum.productms.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import com.nexeum.productms.repository.ProductRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private S3Client s3client;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Flux<Product> getAllProducts() {
        return productRepository.findAll()
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found")))
                .onErrorResume(e -> {
                    log.error("Error getting all products: ", e);
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    @Override
    public Mono<ResponseEntity<ServiceResponse>> addProduct(String name, String description, String brandName, BigDecimal pricePerUnit, BigDecimal productWholeSalePrice, Long noOfStocks) {
        log.info("Product details: name={}, description={}, brandName={}, pricePerUnit={}, productWholeSalePrice={}, noOfStocks={}", name, description, brandName, pricePerUnit, productWholeSalePrice, noOfStocks); // Log the product details
        Product product = new Product();
        product.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        product.setName(name);
        product.setDescription(description);
        product.setBrandName(brandName);
        product.setPricePerUnit(pricePerUnit);
        product.setProductWholeSalePrice(productWholeSalePrice);
        product.setNoOfStocks(noOfStocks);
        return productRepository.save(product)
                .map(savedProduct -> ResponseEntity.ok(new ServiceResponse("200", "Product added successfully")))
                .onErrorResume(e -> {
                    log.error("Error adding product: ", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ServiceResponse("500", "Internal server error")));
                });
    }
}
