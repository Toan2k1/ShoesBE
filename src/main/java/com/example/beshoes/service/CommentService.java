package com.example.beshoes.service;

import com.example.beshoes.models.Comment;
import com.example.beshoes.models.request.AddCommentRequest;

public interface CommentService {
    Comment addComment( AddCommentRequest request);

}
