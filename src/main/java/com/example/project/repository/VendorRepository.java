package com.example.project.repository;

import com.example.project.entity.Vendor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VendorRepository {

    Flux<Vendor> findAllVendors();
    Mono<Vendor> saveVendor(Vendor vendor);

    Mono<Vendor> updateVendor(Vendor newVendor);

}
