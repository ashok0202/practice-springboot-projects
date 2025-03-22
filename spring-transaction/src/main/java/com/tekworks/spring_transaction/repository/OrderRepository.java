package com.tekworks.spring_transaction.repository;

import com.tekworks.spring_transaction.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
