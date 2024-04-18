package com.nexeum.orderms.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GetAllOrderResponse {
    private List<Order> orders;
}
