package com.example.project.controller;

import com.example.project.dto.VendorDto;
import com.example.project.service.VendorService;
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
public class VendorRoute {

    private final ModelMapperUtil mapperUtil;
    private final VendorService vendorService;

    @Bean
    public RouterFunction<ServerResponse> vendorRoutes() {
        return route(GET("/vendor/all"),
                request -> ok().body(
                        vendorService.retrieveAll()
                                     .map(vendor -> mapperUtil.dtoBuilder(vendor, VendorDto.class)), VendorDto.class));
    }

}
