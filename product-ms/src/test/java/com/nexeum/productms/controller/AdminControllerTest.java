package com.nexeum.productms.controller;

import com.nexeum.productms.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void testAddProduct() {
        // Arrange
        MultipartFile imageFile = new MockMultipartFile("imageFile", new byte[]{});
        String name = "Product Name";
        String description = "Product Description";
        String brandName = "Brand Name";
        BigDecimal pricePerUnit = BigDecimal.valueOf(10.99);
        BigDecimal productWholeSalePrice = BigDecimal.valueOf(8.99);
        Long noOfStocks = 100L;

        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        when(productService.addProduct(any(MultipartFile.class), anyString(), anyString(), anyString(), any(BigDecimal.class), any(BigDecimal.class), anyLong())).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Object> actualResponse = adminController.addProduct(imageFile, name, description, brandName, pricePerUnit, productWholeSalePrice, noOfStocks);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }
}