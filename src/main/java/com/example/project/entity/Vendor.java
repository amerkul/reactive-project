package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendors")
public class Vendor extends User {

    @Column("account_number")
    private String accountNumber;
    private String name;
    private List<Business> businesses;

}
