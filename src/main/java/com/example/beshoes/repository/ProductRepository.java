package com.example.beshoes.repository;


import com.example.beshoes.models.Category;
import com.example.beshoes.models.Product;
import com.example.beshoes.models.request.FindProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findProductByCategory(Category category);
    @Query("SELECT p FROM Product p WHERE CONCAT(p.name, ' ', p.categoryName, ' ', p.color, ' ', p.price) LIKE %?1%")
    public List<Product> search(String keyword);



}
