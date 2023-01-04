package com.example.project.repository.impl;

import com.example.project.entity.Order;
import com.example.project.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Repository
@AllArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private static final String INSERT_ORDER = """
            INSERT INTO orders (total_cost, created, user_id) VALUES (:totalCost, NOW(), :userId)")
            """;

    private final DatabaseClient client;

    @Override
    public Mono<Order> saveOrder(BigDecimal totalCost, long userId) {
        return client.sql(INSERT_ORDER)
                .bind("totalCost", totalCost)
                .bind("userId", userId)
                .fetch()
                .first()
                .log()
                .map(row -> new Order(Long.parseLong(row.get("order_id").toString())));
    }

}
