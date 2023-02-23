package com.example.project.repository;

import com.example.project.entity.Business;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends ReactiveCrudRepository<Business, Long> {

}
