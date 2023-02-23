package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Map;

@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "billings")
public class Billing {

    @Id
    @Column("billing_id")
    private long id;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    private long phoneId;
    private long addressId;
    private long userId;
    @Transient
    private Phone phone;
    @Transient
    private User user;
    @Transient
    private Address address;
    @Transient
    private List<Payment> payments;

    public static Billing fromRow(Map<String, Object> row, List<Map<String, Object>> rows) {
        if (row.get("billing_id") == null) {
            return null;
        }
        return Billing.builder()
                      .id(Long.parseLong(row.get("billing_id")
                                            .toString()))
                      .firstName(row.get("first_name")
                                    .toString())
                      .lastName(row.get("last_name")
                                   .toString())
                      .phone(Phone.fromRow(row))
                      .address(Address.fromRow(row))
                      .payments(rows.stream()
                                    .filter(r -> r.get("billing_id")
                                                  .toString()
                                                  .equals(row.get("billing_id")
                                                             .toString()))
                                    .map(Payment::fromRow)
                                    .distinct()
                                    .toList())
                      .build();
    }

}
