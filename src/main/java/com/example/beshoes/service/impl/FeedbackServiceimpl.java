package com.example.beshoes.service.impl;

import com.example.beshoes.models.FeedBack;
import com.example.beshoes.models.User;
import com.example.beshoes.models.request.AddFeedbackRequest;
import com.example.beshoes.repository.FeedBackRepository;
import com.example.beshoes.service.CustomUserDetailService;
import com.example.beshoes.service.FeedbackService;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceimpl implements FeedbackService {

    private final CustomUserDetailService userDetailsService;
    private final FeedBackRepository feedBackRepository;

    public FeedbackServiceimpl(CustomUserDetailService userDetailsService, FeedBackRepository feedBackRepository) {
        this.userDetailsService = userDetailsService;
        this.feedBackRepository = feedBackRepository;
    }

    @Override
    public FeedBack addFeedback(AddFeedbackRequest request) {
        var user = new User();
        user.setUsername(userDetailsService.getPrincipal().getUsername());
        user.setPassword(userDetailsService.getPrincipal().getPassword());
        user.setId(userDetailsService.getPrincipal().getId());
        var feedback = new FeedBack();
        feedback.setUser(user);
        feedback.setContent(request.getContent());
        feedback.setUserId(user.getId());
        feedback.setName(user.getUsername());
        return feedBackRepository.save(feedback);
    }
    @Override
    public boolean deleteFeedback(long id) {
        return feedBackRepository.findById(id).map(category -> {
            feedBackRepository.deleteById(category.getId());
            return true;
        }).orElse(false);
    }
}
