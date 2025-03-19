package application.havenskin.controllers;

import application.havenskin.dataAccess.FeedbackDTO;
import application.havenskin.models.Feedbacks;
import application.havenskin.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/haven-skin/feedbacks")
public class FeebackController {
    @Autowired
    private FeedbackService feedbackService;
    @GetMapping("/{email}/{productName}")
    public Feedbacks getFeedbacks(@PathVariable String email, @PathVariable String productName,@RequestBody FeedbackDTO feedbacks) {
        return feedbackService.createFeedbackByUserIdAndProductId(email, productName, feedbacks);
    }
} 
