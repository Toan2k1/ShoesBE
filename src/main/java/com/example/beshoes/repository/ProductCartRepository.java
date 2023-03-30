package com.example.beshoes.repository;



import com.example.beshoes.models.Cart;
import com.example.beshoes.models.Product;
import com.example.beshoes.models.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {
    ProductCart findByCartAndProduct(Cart cart, Product product);

    List<ProductCart> findByCart(Cart cart);
}
