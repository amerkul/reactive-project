package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private long id;
    private String role;
    private String username;
    private String password;
    private String email;
    private List<BillingDto> billings;
    private List<OrderDto> orders;

}
