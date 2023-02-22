package com.example.project.repository;

import com.example.project.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository{

    Flux<Customer> findAllCustomers();
    Mono<Customer> saveCustomer(long userId, String email);

    Mono<Void> updateCustomer(long userId, String email);


}
