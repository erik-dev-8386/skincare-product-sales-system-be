package application.havenskin.services;

import application.havenskin.dataAccess.FeedbackDTO;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Feedbacks;
import application.havenskin.models.Products;
import application.havenskin.models.Users;
import application.havenskin.repositories.FeedbacksRepository;
import application.havenskin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbacksRepository feedbacksRepository;
    @Autowired
    private Mapper mapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductService productService;
    public List<Feedbacks> getAllFeedbacks() {
        return feedbacksRepository.findAll();
    }
    public Feedbacks getFeedbackById(String id) {
        return feedbacksRepository.findById(id).get();
    }
    public Feedbacks addFeedback(Feedbacks feedback) {
        return feedbacksRepository.save(feedback);
    }
    public Feedbacks updateFeedback(String id, FeedbackDTO feedback) {
        Feedbacks x = feedbacksRepository.findById(id).orElseThrow(() -> new RuntimeException("Feedback not found"));
        mapper.updateFeedbacks(x, feedback);
        return feedbacksRepository.save(x);
    }
    public List<Feedbacks> addListOfFeedbacks(List<Feedbacks> feedbacks) {
        return feedbacksRepository.saveAll(feedbacks);
    }

    public Feedbacks createFeedbackByUserIdAndProductId(String email, String productName, FeedbackDTO feedback) {
        Users x = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        String userId = x.getUserId();
        Products products = productService.getProductByName(productName);
        if (products == null) {
            throw new RuntimeException("Product not found");
        }
        String productId = products.getProductId();
        Feedbacks feedbacks = new Feedbacks();
        feedbacks.setUserId(userId);
        feedbacks.setProductId(productId);
        feedbacks.setFeedbackDate(new Date());
        feedbacks.setFeedbackContent(feedback.getFeedbackContent());
        feedbacks.setProducts(products);
        feedbacks.setUsers(x);
        return feedbacksRepository.save(feedbacks);
    }
//    public Feedbacks updateFeedbackById(String email,String productName,String, FeedbackDTO feedback) {
//        Users x = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
//        String userId = x.getUserId();
//        Products products = productService.getProductByName(productName);
//        if (products == null) {
//            throw new RuntimeException("Product not found");
//        }
//
//    }
}
