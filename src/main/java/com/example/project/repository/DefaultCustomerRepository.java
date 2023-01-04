package com.example.project.repository;


import com.example.project.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultCustomerRepository extends CustomerRepository, ReactiveCrudRepository<Customer, Long> {

}
