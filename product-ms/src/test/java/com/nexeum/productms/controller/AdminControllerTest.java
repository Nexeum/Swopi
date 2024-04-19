package com.nexeum.productms.controller;

import com.nexeum.productms.dto.response.ServiceResponse;
import com.nexeum.productms.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void testAddProduct() {
        // Arrange
        FilePart filePart = Mockito.mock(FilePart.class);
        String name = "Test Product Name";
        String description = "Test Product Description";
        String brandName = "Test Brand Name";
        BigDecimal pricePerUnit = BigDecimal.valueOf(99.99);
        BigDecimal productWholeSalePrice = BigDecimal.valueOf(79.99);
        Long noOfStocks = 100L;

        ServiceResponse serviceResponse = new ServiceResponse("200", "Product added successfully");
        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(serviceResponse);
        when(productService.addProduct(any(), anyString(), anyString(), anyString(), any(BigDecimal.class), any(BigDecimal.class), anyLong()))
                .thenReturn(Mono.just(expectedResponse));

        // Act
        Mono<ResponseEntity<ServiceResponse>> actualResponse = adminController.addProduct(Mono.just(filePart), name, description, brandName, pricePerUnit, productWholeSalePrice, noOfStocks);

        // Assert
        StepVerifier.create(actualResponse)
                .expectNext(expectedResponse)
                .verifyComplete();
    }
}