package com.nexeum.orderms.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductItem {
    private Long productId;
    private String productName;
    private Long productQuantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;
    private String productImageUrl;
}
