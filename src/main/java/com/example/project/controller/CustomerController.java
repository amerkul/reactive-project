package com.example.project.controller;

import com.example.project.dto.CustomerDto;
import com.example.project.entity.Customer;
import com.example.project.entity.User;
import com.example.project.service.CustomerService;
import com.example.project.util.ModelMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ModelMapperUtil mapperUtil;

    @PostMapping("/customer/create")
    public Mono<Void> create(@RequestBody CustomerDto customerDto) {
        return customerService.saveCustomerWithDetails(mapperUtil.entityBuilder(customerDto, Customer.class));
    }

}
