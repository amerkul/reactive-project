package com.example.project.controller;

import com.example.project.dto.CustomerDto;
import com.example.project.entity.Customer;
import com.example.project.service.CustomerService;
import com.example.project.util.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final ModelMapperUtil mapperUtil;

    @PostMapping("/create")
    public Mono<Void> create(@RequestBody CustomerDto customerDto) {
        log.info("CustomerDto: " + customerDto);
        return customerService.saveCustomerWithDetails(mapperUtil.entityBuilder(customerDto, Customer.class));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteById(@PathVariable("id") long id) {
        return customerService.deleteUserById(id);
    }

    @PutMapping("/update/{id}")
    public Mono<Void> updateById(@PathVariable("id") long id, @RequestBody CustomerDto newCustomerDto) {
        return customerService.updateCustomer(id, mapperUtil.entityBuilder(newCustomerDto, Customer.class));
    }

}
