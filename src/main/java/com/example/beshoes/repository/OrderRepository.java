package com.example.beshoes.repository;

import com.example.beshoes.models.Order;
import com.example.beshoes.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(String status);
    List<Order> findByUserId(long userId);

    Optional<Order> findById(long orderId);
    @Query("select o from Order o where o.status = ?1 and o.name = ?2")
    List<Order> search(String status, String name);
}
