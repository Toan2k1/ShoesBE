package com.example.beshoes.repository;


import com.example.beshoes.models.Order;
import com.example.beshoes.models.OrderDetail;
import com.example.beshoes.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDeatailRepository extends JpaRepository<OrderDetail, Long> {
    OrderDetail findByProductAndAndOrder(Product product, Order order);
}
