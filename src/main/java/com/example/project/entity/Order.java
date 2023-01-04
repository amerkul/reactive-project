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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @Column("order_id")
    private long id;
    @Column("total_cost")
    private BigDecimal totalCost;
    private LocalDateTime created;
    @Transient
    private Customer customer;
    
    @Transient
    private List<OrderDetail> orderDetails;

    public Order(long id) {
        this.id = id;
    }

    public static Order fromRow(Map<String, Object> row, List<Map<String, Object>> rows) {
        if (row.get("order_id") == null) {
            return null;
        }
        long orderId = Long.parseLong(row.get("order_id").toString());
        return Order.builder()
                    .id(orderId)
                    .totalCost(new BigDecimal(row.get("total_cost").toString()))
                    .created(LocalDateTime.parse(row.get("created").toString()))
                    .orderDetails(rows.stream()
                                      .filter(r -> Long.parseLong(r.get("order_id").toString()) == orderId)
                                      .map(OrderDetail::fromRows)
                                      .toList())
                    .build();
    }

}
