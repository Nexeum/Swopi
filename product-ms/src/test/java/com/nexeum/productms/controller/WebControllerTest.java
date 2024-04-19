package com.nexeum.productms.controller;

import com.nexeum.productms.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        List<Object> productList = new ArrayList<>();
        productList.add(new Object());
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(productList, HttpStatus.OK);
        when(productService.getAllProducts()).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Object> actualResponse = webController.getAllProducts();

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetAllProducts_NoProducts() {
        // Arrange
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        when(productService.getAllProducts()).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Object> actualResponse = webController.getAllProducts();

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }
}