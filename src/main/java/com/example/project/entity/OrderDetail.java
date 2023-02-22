package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @Column("order_details_id")
    private long id;
    private int number;
    private BigDecimal cost;
    private long businessId;
    private long orderId;
    @Transient
    private Order order;
    @Transient
    private Business business;

    public static OrderDetail fromRows(Map<String, Object> row) {
        return OrderDetail.builder()
                .id(Long.parseLong(row.get("order_details_id").toString()))
                .number(Integer.parseInt(row.get("number").toString()))
                .cost(new BigDecimal(row.get("cost").toString()))
                .business(Business.fromRow(row))
                .build();
    }

}
