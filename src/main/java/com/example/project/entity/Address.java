package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @Column("address_id")
    private long id;
    private String country;
    private String state;
    @Column("state_abbreviation")
    private String stateAbbreviation;
    private String city;
    private String street;
    private int zip;
    private LocalDate created;
    private LocalDate updated;
    @Transient
    List<Billing> billings;
    @Transient
    List<Business> businesses;

    public static Address fromRow(Map<String, Object> row) {
        return Address.builder()
                      .id(Long.parseLong(row.get("address_id").toString()))
                      .country(row.get("country").toString())
                      .state(row.get("state").toString())
                      .stateAbbreviation(row.get("state_abbreviation").toString())
                      .city(row.get("city").toString())
                      .street(row.get("street").toString())
                      .zip(Integer.parseInt(row.get("zip").toString()))
                      .created(LocalDateTime.parse(row.get("address_created").toString()).toLocalDate())
                      .updated(LocalDateTime.parse(row.get("address_updated").toString()).toLocalDate())
                      .build();
    }

}
