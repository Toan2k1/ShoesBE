package com.example.beshoes.controller;

import com.example.beshoes.models.FeedBack;
import com.example.beshoes.models.request.AddFeedbackRequest;
import com.example.beshoes.models.response.FeedbackRespon;
import com.example.beshoes.repository.FeedBackRepository;
import com.example.beshoes.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedbackController {
    private final FeedBackRepository feedBackRepository;
    private final ModelMapper modelMapper;
    private final FeedbackService feedbackService;

    public FeedbackController(FeedBackRepository feedBackRepository, ModelMapper modelMapper, FeedbackService feedbackService) {
        this.feedBackRepository = feedBackRepository;
        this.modelMapper = modelMapper;
        this.feedbackService = feedbackService;
    }
    @PostMapping("addFeedBack")
    public ResponseEntity<FeedbackRespon> addFeedback(@RequestBody AddFeedbackRequest request){
        var feedback = feedbackService.addFeedback(request);
        var feedBackResponse = modelMapper.map(feedback, FeedbackRespon.class);
        return new ResponseEntity(feedBackResponse, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable long id){
        return feedbackService.deleteFeedback(id);
    }
    @GetMapping("getListFeedback")
    public List<FeedBack> getListFeedback(){
        return feedBackRepository.findAll();
    }
}
