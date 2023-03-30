package com.example.beshoes.service;



import com.example.beshoes.models.Category;
import com.example.beshoes.models.request.AddCategoryRequest;
import com.example.beshoes.models.request.EditCategoryRequest;

import java.util.List;

public interface CategoryService {

    Category addCategory(AddCategoryRequest request);

    boolean deleteCategory(long id);

    Category editCategory(EditCategoryRequest request , long id);

    List<Category> listCate();

    Category getCategoryById(long id);


}
