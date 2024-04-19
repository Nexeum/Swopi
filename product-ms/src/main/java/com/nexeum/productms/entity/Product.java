package com.nexeum.productms.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private String brandName;
    private BigDecimal pricePerUnit;
    private BigDecimal productWholeSalePrice;
    private Long noOfStocks;
    private String productImageUrl;
}