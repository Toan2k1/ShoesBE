package com.example.beshoes.models.response;

import lombok.Data;

import java.util.List;

@Data
public class AddCategoryResponse {

    private long id;
    private String name;

    private List<AddProductResponse> products;

}
