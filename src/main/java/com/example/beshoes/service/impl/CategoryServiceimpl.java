package com.example.beshoes.service.impl;

import com.example.beshoes.models.Category;
import com.example.beshoes.models.request.AddCategoryRequest;
import com.example.beshoes.models.request.EditCategoryRequest;
import com.example.beshoes.repository.CategoryRepository;
import com.example.beshoes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceimpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category addCategory(AddCategoryRequest request) {
        var cate = new Category();
        cate.setName(request.getName());
        return categoryRepository.save(cate);
    }

    @Override
    public boolean deleteCategory(long id) {
        return categoryRepository.findById(id).map(category -> {
            categoryRepository.deleteById(category.getId());
            return true;
        }).orElse(false);
    }

    @Override
    public Category editCategory(EditCategoryRequest request , long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(request.getName());
                    return categoryRepository.save(category);
                }).orElse(null);
    }

    @Override
    public List<Category> listCate() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
