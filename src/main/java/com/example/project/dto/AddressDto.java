package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private long id;
    private String country;
    private String state;
    private String stateAbbreviation;
    private String city;
    private String street;
    private int zip;
    private LocalDate created;
    private LocalDate updated;

}
