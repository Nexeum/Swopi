package com.nexeum.orderms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Long productId;
    private String productName;
    private Long productQuantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;
    private String productImageUrl;

    @JsonIgnore
    @ManyToOne
    private ProductOrder order;
}
