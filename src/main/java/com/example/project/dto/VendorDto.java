package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDto extends UserDto {

    private String accountNumber;
    private String name;
    private List<BusinessDto> businesses;

}
