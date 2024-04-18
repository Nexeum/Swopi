package com.nexeum.orderms.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductItem {
    private Long id;
    private Long productId;
    private String productName;
    private Long productQuantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;
    private String productImageUrl;
}
