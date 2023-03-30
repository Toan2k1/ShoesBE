package com.example.beshoes.service;


import com.example.beshoes.models.Product;
import com.example.beshoes.models.request.AddProductRequest;
import com.example.beshoes.models.request.EditProductRequest;
import com.example.beshoes.models.request.FindProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Product add(AddProductRequest request, MultipartFile image) throws IOException;

    Product update(EditProductRequest request, MultipartFile image) throws IOException;

    List<Product> getAll();

    boolean delete(long id);


    List<Product> getProductByCategoryName(long id);

    Product getProductById(long id);


    List<Product> listAll(String keyword);
}
