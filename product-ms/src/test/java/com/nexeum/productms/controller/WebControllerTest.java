package com.nexeum.productms.controller;

import com.nexeum.productms.entity.Product;
import com.nexeum.productms.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private WebController webController;

    @Test
    void testGetAllProducts() {
        // Arrange
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(productService.getAllProducts()).thenReturn(Flux.fromIterable(productList));

        // Act
        Mono<ResponseEntity<List<Product>>> actualResponse = webController.getAllProducts();

        // Assert
        StepVerifier.create(actualResponse)
                .expectNextMatches(response -> response.getStatusCode().equals(HttpStatus.OK) && productList.equals(response.getBody()))
                .verifyComplete();
    }

    @Test
    void testGetAllProducts_NoProducts() {
        // Arrange
        when(productService.getAllProducts()).thenReturn(Flux.empty());

        // Act
        Mono<ResponseEntity<List<Product>>> actualResponse = webController.getAllProducts();

        // Assert
        StepVerifier.create(actualResponse)
                .expectNextMatches(response -> response.getStatusCode().equals(HttpStatus.OK) && Objects.requireNonNull(response.getBody()).isEmpty())
                .verifyComplete();
    }
}