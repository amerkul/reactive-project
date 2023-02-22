package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDto {

    private long id;
    private String favour;
    private BigDecimal cost;
    private PhoneDto phone;
    private AddressDto address;

}
