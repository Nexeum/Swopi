package com.nexeum.productms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "Text")
    private String description;
    private String brandName;
    private BigDecimal pricePerUnit;
    private BigDecimal productWholeSalePrice;
    private Long noOfStocks;
    private String productImageUrl;
}
