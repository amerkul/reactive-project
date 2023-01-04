package com.example.project.repository;

import com.example.project.entity.Phone;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends ReactiveCrudRepository<Phone, Long> {

}
