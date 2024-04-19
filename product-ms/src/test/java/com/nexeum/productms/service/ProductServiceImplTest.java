package com.nexeum.productms.service;

import com.nexeum.productms.dto.response.ServiceResponse;
import com.nexeum.productms.entity.Product;
import com.nexeum.productms.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
    void testAddProduct() {
        Product product = new Product();
        product.setName("Test Product Name");
        product.setDescription("Test Product Description");
        product.setBrandName("Test Brand Name");
        product.setPricePerUnit(BigDecimal.valueOf(99.99));
        product.setProductWholeSalePrice(BigDecimal.valueOf(79.99));
        product.setNoOfStocks(100L);

        FilePart imageFile = Mockito.mock(FilePart.class);
        Mono<FilePart> imageFileMono = Mono.just(imageFile);

        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(product));

        Mono<ResponseEntity<ServiceResponse>> response = productService.addProduct(imageFileMono, product.getName(), product.getDescription(), product.getBrandName(), product.getPricePerUnit(), product.getProductWholeSalePrice(), product.getNoOfStocks());

        StepVerifier.create(response)
                .assertNext(res -> {
                    assertEquals(HttpStatus.OK, res.getStatusCode());
                    verify(productRepository, times(1)).save(any(Product.class));
                })
                .verifyComplete();
    }

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