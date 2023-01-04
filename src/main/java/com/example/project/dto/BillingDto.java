package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingDto {

    private long id;
    private String firstName;
    private String lastName;
    private PhoneDto phone;
    private AddressDto address;
    private List<PaymentDto> payments;

}
