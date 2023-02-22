package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @Column("payment_id")
    private long id;
    private BigDecimal amount;
    private LocalDateTime created;
    private long billingId;
    private Billing billing;

    public static Payment fromRow(Map<String, Object> row) {
        if (row.get("payment_id") == null) {
            return null;
        }
        return Payment.builder()
                .id(Long.parseLong(row.get("payment_id").toString()))
                .amount(new BigDecimal(row.get("amount").toString()))
                .created(LocalDateTime.parse(row.get("payment_created").toString()))
                .build();
    }

}
