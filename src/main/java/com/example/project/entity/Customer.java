package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer extends User {

    private String email;
    @Transient
    private List<Order> orders;

    public Customer(long id) {
        super(id);
    }

    @Builder
    public Customer(long id, String role, String username, String password, List<Billing> billings, String email, List<Order> orders) {
        super(id, role, username, password, billings);
        this.email = email;
        this.orders = orders;
    }

    public static Mono<Customer> fromRows(List<Map<String, Object>> rows) {
        return Mono.just(Customer.builder()
                                 .id(Long.parseLong(rows.get(0).get("user_id").toString()))
                                 .role(rows.get(0).get("role").toString())
                                 .username(rows.get(0).get("username").toString())
                                 .password(rows.get(0).get("password").toString())
                                 .email((String) rows.get(0).get("email"))
                                 .orders(rows.stream()
                                             .map(row -> Order.fromRow(row, rows))
                                             .filter(Objects::nonNull)
                                             .toList())
                                 .billings(rows.stream()
                                               .map(row -> Billing.fromRow(row, rows))
                                               .filter(Objects::nonNull)
                                               .toList())
                                 .build()
        );
    }

}
