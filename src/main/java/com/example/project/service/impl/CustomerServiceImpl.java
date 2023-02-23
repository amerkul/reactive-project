package com.example.project.service.impl;

import com.example.project.entity.Address;
import com.example.project.entity.Billing;
import com.example.project.entity.Customer;
import com.example.project.entity.Order;
import com.example.project.entity.OrderDetail;
import com.example.project.entity.Payment;
import com.example.project.entity.Phone;
import com.example.project.entity.User;
import com.example.project.repository.AddressRepository;
import com.example.project.repository.BillingRepository;
import com.example.project.repository.DefaultCustomerRepository;
import com.example.project.repository.OrderDetailRepository;
import com.example.project.repository.OrderRepository;
import com.example.project.repository.PaymentRepository;
import com.example.project.repository.PhoneRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final PaymentRepository paymentRepository;

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
        return customerRepository.findAllCustomers();
    }

    @Override
    @Transactional
    public Mono<Void> saveCustomerWithDetails(Customer customer) {
        return userRepository.save(new User(customer.getRole(), customer.getUsername(), customer.getPassword()))
                             .flatMap(savedUser -> customerRepository.saveCustomer(savedUser.getId(), customer.getEmail()))
                             .flatMapMany(savedCustomer -> orderRepository.saveAll(updateOrders(customer.getOrders(), savedCustomer))
                                                                          .flatMap(savedOrder -> orderDetailRepository.saveAll(updateOrderDetails(savedOrder)))
                                                                          .thenMany(Flux.fromIterable(customer.getBillings()))
                                                                          .flatMap(billing -> updateBilling(billing, savedCustomer))
                                                                          .flatMap(savedBilling -> paymentRepository.saveAll(updatePayments(savedBilling))))

                             .then();
    }

    @Override
    @Transactional
    public Mono<Void> updateCustomer(long id, Customer newCustomer) {
        return userRepository.findById(id)
                             .flatMap(user -> Mono.just(newCustomer)
                                                  .map(c -> {
                                                      user.setPassword(c.getPassword());
                                                      user.setRole(c.getRole());
                                                      user.setUsername(c.getUsername());
                                                      return user;
                                                  }))
                             .flatMap(userRepository::save)
                             .flatMapMany(user -> Flux.fromIterable(newCustomer.getBillings())
                                                      .flatMap(newBilling -> updateBilling(newBilling, user)))
                             .then(customerRepository.findById(id)
                                                     .flatMap(customer -> Mono.just(newCustomer)
                                                                              .map(c -> {
                                                                                  customer.setEmail(c.getEmail());
                                                                                  return customer;
                                                                              }))
                                                     .flatMap(customer -> customerRepository.updateCustomer(customer.getId(), customer.getEmail())))
                             .then();
    }

    @Override
    @Transactional
    public Mono<Void> deleteUserById(long id) {
        return userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Mono<Customer> retrieveCustomerById(long id) {
        return customerRepository.findById(id);
    }

    private Mono<Billing> updateBilling(Billing billing, User savedCustomer) {
        return addressRepository.save(billing.getAddress())
                                .map(savedAddress -> updateBillingAddress(billing, savedAddress))
                                .then(phoneRepository.save(billing.getPhone())
                                                     .map(savedPhone -> updateBillingPhone(billing, savedPhone)))
                                .then(Mono.just(updateBillingUser(billing, savedCustomer)))
                                .flatMap(billingRepository::save);
    }

    private Billing updateBillingUser(Billing billing, User user) {
        billing.setUserId(user.getId());
        return billing;
    }

    private Billing updateBillingAddress(Billing billing, Address address) {
        billing.setAddressId(address.getId());
        return billing;
    }

    private Billing updateBillingPhone(Billing billing, Phone phone) {
        billing.setPhoneId(phone.getId());
        return billing;
    }

    private List<Order> updateOrders(List<Order> orders, Customer savedCustomer) {
        return orders.stream()
                     .peek(order -> {
                         order.setUserId(savedCustomer.getId());
                         order.setCreated(LocalDateTime.now());
                     })
                     .toList();
    }

    private List<OrderDetail> updateOrderDetails(Order savedOrder) {
        return savedOrder.getOrderDetails()
                         .stream()
                         .peek(orderDetail -> {
                             orderDetail.setOrderId(savedOrder.getId());
                             orderDetail.setBusinessId(orderDetail.getBusiness()
                                                                  .getId());
                         })
                         .toList();
    }

    private List<Payment> updatePayments(Billing savedBilling) {
        return savedBilling.getPayments()
                           .stream()
                           .peek(payment -> {
                               payment.setBillingId(savedBilling.getId());
                               payment.setCreated(LocalDateTime.now());
                           })
                           .toList();
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
