package com.nexeum.orderms.dto.request;

import java.math.BigDecimal;
import java.util.List;

public class CreateOrderRequest {
    private String cartId;
    private BigDecimal totalOrderPrice;
    private List<OrderProductItem> orderItems;
}
