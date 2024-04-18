package com.nexeum.orderms.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderRequest {
    private Long cartId;
    private BigDecimal totalOrderPrice;
    private List<OrderProductItem> orderItems;
}
