package application.havenskin.controllers;

import application.havenskin.dataAccess.FeedbackDTO;
import application.havenskin.models.Feedbacks;
import application.havenskin.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/haven-skin/feedbacks")
public class FeebackController {
    @Autowired
    private FeedbackService feedbackService;
    @PostMapping("/{email}/{productName}")
    // rating -> byte : 1 - 5
    public Feedbacks getFeedbacks(@PathVariable String email, @PathVariable String productName,@RequestBody FeedbackDTO feedbacks) {
        return feedbackService.createFeedbackByUserIdAndProductId(email, productName, feedbacks);
    }
    // trưng ra
    @GetMapping("/{email}/{productName}")
    public Feedbacks getFeedbacksByEmail(@PathVariable String email, @PathVariable String productName) {
        return feedbackService.searchFeedbackById(email, productName);
    }
    // lấy trung bình sao của khách hàng
    @GetMapping("/average-rating/{productName}")
    public double getAverageRating(@PathVariable String productName) {
        return feedbackService.calculateAverageRating(productName);
    }

    // lấy số lượng sao của tất cả khách hàng
    @GetMapping("/get-star/by-customer/{productName}")
    public Map<Byte, Integer> getStarsByCustomer(@PathVariable String productName) {
        return feedbackService.getRatingByAllUsers(productName);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping
    public List<Feedbacks> getFeedbacks() {
        return feedbackService.getFeedbacks();
    }
} 
