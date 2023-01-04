package com.example.project.service;

import com.example.project.entity.User;
import reactor.core.publisher.Flux;

public interface OrderService {

    Flux<User> retrieveAllUsersOrders();

}
