package com.example.project.service.impl;

import com.example.project.entity.Customer;
import com.example.project.entity.User;
import com.example.project.repository.AddressRepository;
import com.example.project.repository.BillingRepository;
import com.example.project.repository.DefaultCustomerRepository;
import com.example.project.repository.OrderDetailRepository;
import com.example.project.repository.OrderRepository;
import com.example.project.repository.PhoneRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;
    private final DefaultCustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final BillingRepository billingRepository;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public Flux<Customer> retrieveAll() {
        return customerRepository.findAllCustomers()
                                 .log();
    }

    @Override
    @Transactional
    public Mono<Void> saveCustomerWithDetails(Customer customer) {
        log.info("Save new customer with details " + customer);
        return userRepository.save(new User(customer.getRole(), customer.getUsername(), customer.getPassword()))
                             .flatMap(savedUser -> customerRepository.saveCustomer(savedUser.getId(), customer.getEmail()))
                             .log()
                             .flatMap(savedCustomer -> Mono.just(customer.getOrders().stream()
                                                                         .map(order -> orderRepository.saveOrder(order.getTotalCost(), savedCustomer.getId()).block())
                                                                         .toList())
                             )
                             .log()
                             .then();
    }

    @Override
    public Flux<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Flux<Customer> retrieveAllCustomers() {
        return customerRepository.findAll();
    }

}
