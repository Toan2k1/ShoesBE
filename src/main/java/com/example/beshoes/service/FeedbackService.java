package com.example.beshoes.service;

import com.example.beshoes.models.FeedBack;
import com.example.beshoes.models.request.AddFeedbackRequest;

public interface FeedbackService {
    FeedBack addFeedback(AddFeedbackRequest request);


    boolean deleteFeedback(long id);
}
