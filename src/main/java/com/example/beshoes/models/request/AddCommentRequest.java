package com.example.beshoes.models.request;

import lombok.Data;

@Data
public class AddCommentRequest {
    private long id;
    private String content;
}
