package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "businesses")
public class Business {

    @Id
    @Column("business_id")
    private long id;
    private String favour;
    private BigDecimal cost;
    private Phone phone;
    private Address address;
    private List<OrderDetail> orderDetails;
    private Vendor vendor;

    public static Business fromRow(Map<String, Object> row) {
        return Business.builder()
                .id(Long.parseLong(row.get("business_id").toString()))
                .favour(row.get("favour").toString())
                .cost(new BigDecimal(row.get("businesses_cost").toString()))
                .build();
    }

}
