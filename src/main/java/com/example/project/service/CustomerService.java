package com.example.project.service;

import com.example.project.entity.Customer;
import com.example.project.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Flux<Customer> retrieveAll();
    Mono<Void> saveCustomerWithDetails(Customer customer);

    Flux<User> retrieveAllUsers();
    Flux<Customer> retrieveAllCustomers();

}
