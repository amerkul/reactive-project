package com.example.project.repository;

import com.example.project.entity.Vendor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DefaultVendorRepository extends ReactiveCrudRepository<Vendor, Long>, VendorRepository {
}
