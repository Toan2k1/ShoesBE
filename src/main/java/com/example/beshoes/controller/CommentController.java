package com.example.beshoes.controller;

import com.example.beshoes.models.request.AddCommentRequest;
import com.example.beshoes.models.response.CommentResponse;
import com.example.beshoes.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    private final CommentService commentService;

    private final ModelMapper modelMapper;

    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("addComment")
    public ResponseEntity<CommentResponse> addComment(@RequestBody AddCommentRequest request){
        var comment = commentService.addComment(request);
        var commentResponse = modelMapper.map(comment, CommentResponse.class);
        return new ResponseEntity(commentResponse, HttpStatus.OK);
    }
}
