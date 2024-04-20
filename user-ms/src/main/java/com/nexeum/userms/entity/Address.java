package com.nexeum.userms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id
    private Long addressId;
    private String addressLine;
    private String city;
    private String pin;
    private String state;
    private String country;
}
