package com.example.project.repository;

import com.example.project.entity.OrderDetail;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends ReactiveCrudRepository<OrderDetail, Long> {

}
