package com.nexeum.orderms.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Order {
    private Long orderId;
    private BigDecimal totalOrderPrice;
    private String orderStatus;
    List<ProductItem> orderItems;
    private Date createdDate;
    private Date updatedDate;
}
