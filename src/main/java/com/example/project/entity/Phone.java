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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phones")
public class Phone {

    @Id
    @Column("phone_id")
    private long id;
    private int number;
    @Column("area_code")
    private int areaCode;
    @Column("country_code")
    private int countryCode;
    private LocalDate created;
    private LocalDate updated;
    @Transient
    private List<Business> businesses;
    @Transient
    private List<Billing> billings;

    public static Phone fromRow(Map<String, Object> row) {
        return Phone.builder()
                .id(Long.parseLong(row.get("phone_id").toString()))
                .number(Integer.parseInt(row.get("phone_number").toString()))
                .areaCode(Integer.parseInt(row.get("area_code").toString()))
                .countryCode(Integer.parseInt(row.get("country_code").toString()))
                .created(LocalDateTime.parse(row.get("phone_created").toString()).toLocalDate())
                .updated(LocalDateTime.parse(row.get("phone_updated").toString()).toLocalDate())
                .build();
    }

}
