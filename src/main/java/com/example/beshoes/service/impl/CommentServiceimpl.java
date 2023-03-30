package com.example.beshoes.service.impl;

import com.example.beshoes.models.Comment;
import com.example.beshoes.models.User;
import com.example.beshoes.models.request.AddCommentRequest;
import com.example.beshoes.repository.CommentRepository;
import com.example.beshoes.repository.ProductRepository;
import com.example.beshoes.service.CommentService;
import com.example.beshoes.service.CustomUserDetailService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceimpl implements CommentService {
    private final ProductRepository productRepository;

    private final CustomUserDetailService userDetailsService;

    private final CommentRepository commentRepository;

    public CommentServiceimpl(ProductRepository productRepository, CustomUserDetailService userDetailsService, CommentRepository commentRepository) {
        this.productRepository = productRepository;
        this.userDetailsService = userDetailsService;
        this.commentRepository = commentRepository;
    }


    @Override
    public Comment addComment(AddCommentRequest request) {
        var product = productRepository.findById(request.getId());
        var user = new User();
        user.setUsername(userDetailsService.getPrincipal().getUsername());
        user.setPassword(userDetailsService.getPrincipal().getPassword());
        user.setId(userDetailsService.getPrincipal().getId());
        user.setCart(userDetailsService.getPrincipal().getCart());

        var comment = new Comment();
        comment.setProduct(product.get());
        comment.setUser(user);
        comment.setContent(request.getContent());
        comment.setProductId(request.getId());
        comment.setUserId(user.getId());
        comment.setName(user.getUsername());

        return commentRepository.save(comment);
    }


}

