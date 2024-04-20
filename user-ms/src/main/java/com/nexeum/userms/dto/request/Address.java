package com.nexeum.userms.dto.request;

import lombok.Data;

@Data
public class Address {
    private String addressLine;
    private String city;
    private String pin;
    private String state;
    private String country;
}
