package com.nexeum.productms.service;

import com.nexeum.productms.entity.Product;
import com.nexeum.productms.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private S3Client s3Client;

    @InjectMocks
    private ProductServiceImpl productService;

    private MultipartFile imageFile;
    private String name;
    private String description;
    private String brandName;
    private BigDecimal pricePerUnit;
    private BigDecimal productWholeSalePrice;
    private Long noOfStocks;

    @BeforeEach
    public void setUp() {
        imageFile = mock(MultipartFile.class);
        name = "Test Product";
        description = "Test Description";
        brandName = "Test Brand";
        pricePerUnit = BigDecimal.valueOf(100);
        productWholeSalePrice = BigDecimal.valueOf(80);
        noOfStocks = 10L;
    }

    @Test
    public void testAddProduct() {
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);

        ResponseEntity<Object> response = productService.addProduct(imageFile, name, description, brandName, pricePerUnit, productWholeSalePrice, noOfStocks);

        verify(productRepository, times(1)).save(any(Product.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<Object> response = productService.getAllProducts();

        verify(productRepository, times(1)).findAll();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}