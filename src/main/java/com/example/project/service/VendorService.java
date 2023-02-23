package com.example.project.service;

import com.example.project.entity.Vendor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VendorService {

    Flux<Vendor> retrieveAll();
    Mono<Void> saveVendorWithDetails(Vendor vendor);

    Mono<Void> updateVendor(long id, Vendor newVendor);

}
