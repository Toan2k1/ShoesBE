package com.example.beshoes.controller;


import com.example.beshoes.models.request.AddCategoryRequest;
import com.example.beshoes.models.request.EditCategoryRequest;
import com.example.beshoes.models.response.AddCategoryResponse;
import com.example.beshoes.models.response.EditCategoryResponse;
import com.example.beshoes.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/cate")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/list")
    public ResponseEntity<List<AddCategoryResponse>> get(){
        var list = categoryService.listCate().stream()
                .map(category -> modelMapper.map(category, AddCategoryResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AddCategoryResponse> add(@RequestBody AddCategoryRequest request){
        var addCate = categoryService.addCategory(request);
        var cateResponse = modelMapper.map(addCate , AddCategoryResponse.class);
        return new ResponseEntity<>(cateResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable long id){
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EditCategoryResponse> edit(@RequestBody EditCategoryRequest request,
                                                     @PathVariable long id){
        var cate = categoryService.editCategory(request, id);
        var editCate =modelMapper.map(cate, EditCategoryResponse.class);

        return new ResponseEntity<>(editCate, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<AddCategoryResponse> getById(@PathVariable long id){
        var cart = categoryService.getCategoryById(id);
        var cartResponse = modelMapper.map(cart, AddCategoryResponse.class);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }
}
