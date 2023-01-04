package com.example.project.controller;

import com.example.project.dto.CustomerDto;
import com.example.project.entity.User;
import com.example.project.service.CustomerService;
import com.example.project.util.ModelMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@AllArgsConstructor
public class CustomerRoute {

    private final CustomerService customerService;
    private final ModelMapperUtil mapperUtil;

    @Bean
    public RouterFunction<ServerResponse> composedRoutes() {
        return route(
                GET("/customer/all"),
                request -> ok().body(
                        customerService.retrieveAll()
                                       .map(customer -> mapperUtil.dtoBuilder(customer, CustomerDto.class)), CustomerDto.class)
        ).and(route(
                GET("/users"),
                request -> ok().body(customerService.retrieveAllUsers(), User.class)
        )).and(route(
                GET("/customers"),
                request -> ok().body(customerService.retrieveAllCustomers()
                                                    .map(customer -> mapperUtil.dtoBuilder(customer, CustomerDto.class)), CustomerDto.class)
        ));
    }

}
