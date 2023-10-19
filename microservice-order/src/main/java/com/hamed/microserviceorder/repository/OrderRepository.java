package com.hamed.microserviceorder.repository;

import com.hamed.microserviceorder.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
