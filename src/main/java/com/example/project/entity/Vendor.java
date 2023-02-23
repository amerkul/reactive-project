package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendors")
public class Vendor extends User {

    @Column("account_number")
    private String accountNumber;
    private String name;
    private List<Business> businesses;

    public Vendor(long id) {
        super(id);
    }

    public Vendor(long id, String accountNumber, String name) {
        super(id);
        this.accountNumber = accountNumber;
        this.name = name;
    }

    @Builder
    public Vendor(long id, String role, String username, String password, List<Billing> billings, String accountNumber, String name, List<Business> businesses) {
        super(id, role, username, password, billings);
        this.accountNumber = accountNumber;
        this.name = name;
        this.businesses = businesses;
    }

    public static Mono<Vendor> fromRows(List<Map<String, Object>> rows) {
        return Mono.just(Vendor.builder()
                               .id(Long.parseLong(rows.get(0)
                                                      .get("user_id")
                                                      .toString()))
                               .role(rows.get(0).get("role").toString())
                               .username(rows.get(0).get("username").toString())
                               .password(rows.get(0).get("password").toString())
                               .accountNumber((String) rows.get(0).get("account_number"))
                               .name((String) rows.get(0).get("name"))
                               .businesses(rows.stream()
                                           .map(Business::fromRowForVendor)
                                           .filter(Objects::nonNull)
                                           .distinct()
                                           .toList())
                               .billings(rows.stream()
                                             .map(row -> Billing.fromRow(row, rows))
                                             .filter(Objects::nonNull)
                                             .distinct()
                                             .toList())
                               .build()
        );
    }

}
