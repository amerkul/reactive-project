package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {


    private long id;
    private int number;
    private int areaCode;
    private int countryCode;
    private LocalDate created;
    private LocalDate updated;

}
