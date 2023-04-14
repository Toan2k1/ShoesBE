package com.example.beshoes.service.impl;


import com.example.beshoes.models.Category;
import com.example.beshoes.models.Product;
import com.example.beshoes.models.request.AddProductRequest;
import com.example.beshoes.models.request.EditProductRequest;
import com.example.beshoes.repository.CategoryRepository;
import com.example.beshoes.repository.ProductRepository;
import com.example.beshoes.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceimpl implements ProductService {
    public final String CURRENT_FOLDER = "C:\\Hosting\\img";
    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceimpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Product add(AddProductRequest request, MultipartFile image) throws IOException {

        String fileName = image.getOriginalFilename();
        try {
            FileCopyUtils.copy(image.getBytes(), new File(CURRENT_FOLDER + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Files.copy(image.getInputStream(), Paths.get(CURRENT_FOLDER + File.separator + image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

        return categoryRepository.findByName(request.getCategoryName())
                .map(category -> {
                    var newProduct = new Product();
                    newProduct.setName(request.getName());
                    newProduct.setQuantity(request.getQuantity());
                    newProduct.setPrice(request.getPrice());
                    newProduct.setCategoryName(request.getCategoryName());
                    newProduct.setDescription(request.getDescription());
                    newProduct.setImage(fileName);
                    newProduct.setColor(request.getColor());
                    newProduct.setSize(request.getSize());
                    newProduct.setCategory(category);
                    return productRepository.save(newProduct);
                }).orElse(null);
    }

    @Override
    public Product update(EditProductRequest request/*, MultipartFile image*/) throws IOException {

        /*String fileName = image.getOriginalFilename();
        try {
            FileCopyUtils.copy(image.getBytes(), new File(CURRENT_FOLDER + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return productRepository.findById(request.getId())
                .map(product -> {
                    product.setName(request.getName());
                    product.setQuantity(request.getQuantity());
                    product.setPrice(request.getPrice());
                    product.setCategoryName(request.getCategoryName());
                    product.setDescription(request.getDescription());
                    product.setColor(request.getColor());
                    product.setSize(request.getSize());
                    /*File filem = new File(CURRENT_FOLDER + product.getImage());
                    if (filem.isFile()) {
                        filem.delete();
                    } else {
                        System.out.println("đ phải file");
                    }
                    product.setImage(fileName);*/
                    return productRepository.save(product);
                }).orElse(null);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public boolean delete(long id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return true;
        }).orElse(false);
    }


    @Override
    public List<Product> getProductByCategoryName(long id) {
        Category category = categoryRepository.findCategoriesById(id).get();
        List<Product> products = productRepository.findProductByCategory(category);
        return products;
    }

    @Override
    public Product getProductById(long id) {
        Optional<Product> product1 = productRepository.findById(id);
        if (product1.isPresent()) {
            return productRepository.getById(id);
        }
        return null;
    }

    @Override
    public List<Product> listAll(String keyword) {
        if (keyword != null) {
            return productRepository.search(keyword);
        }
        return productRepository.findAll();
    }


}
