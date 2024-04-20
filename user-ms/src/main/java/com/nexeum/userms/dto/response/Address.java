package com.nexeum.userms.dto.response;

import lombok.Data;

@Data
public class Address {
    private Long addressId;
    private String addressLine;
    private String city;
    private String pin;
    private String state;
    private String country;
}
