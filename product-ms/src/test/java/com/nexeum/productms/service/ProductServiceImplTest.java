package com.nexeum.productms.service;

import com.nexeum.productms.entity.Product;
import com.nexeum.productms.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testGetAllProducts() {
        String name = "Test Product Name";
        String description = "Test Product Description";
        String brandName = "Test Brand Name";
        BigDecimal pricePerUnit = BigDecimal.valueOf(99.99);
        BigDecimal productWholeSalePrice = BigDecimal.valueOf(79.99);
        Long noOfStocks = 100L;

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setBrandName(brandName);
        product.setPricePerUnit(pricePerUnit);
        product.setProductWholeSalePrice(productWholeSalePrice);
        product.setNoOfStocks(noOfStocks);

        when(productRepository.findAll()).thenReturn(Flux.just(product));

        Flux<Product> products = productService.getAllProducts();

        StepVerifier.create(products)
                .assertNext(prod -> {
                    assertEquals(name, prod.getName());
                    assertEquals(description, prod.getDescription());
                    assertEquals(brandName, prod.getBrandName());
                    assertEquals(pricePerUnit, prod.getPricePerUnit());
                    assertEquals(productWholeSalePrice, prod.getProductWholeSalePrice());
                    assertEquals(noOfStocks, prod.getNoOfStocks());
                })
                .verifyComplete();

        verify(productRepository, times(1)).findAll();
    }
}