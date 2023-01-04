package com.example.project.repository;

import com.example.project.entity.Order;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface OrderRepository{
    Mono<Order> saveOrder(BigDecimal totalCost, long userId);

}
