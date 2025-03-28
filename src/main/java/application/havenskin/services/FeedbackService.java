package application.havenskin.services;

import application.havenskin.dataAccess.FeedbackDTO;
import application.havenskin.enums.FeedBackEnum;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Feedbacks;
import application.havenskin.models.Products;
import application.havenskin.models.Users;
import application.havenskin.repositories.FeedbacksRepository;
import application.havenskin.repositories.ProductsRepository;
import application.havenskin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ProductsRepository productsRepository;

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
        feedbacks.setRating(feedback.getRating());
        feedbacks.setStatus(FeedBackEnum.ACTIVE.getFeedBack_status());
        feedbacks.setUsers(x);
        return feedbacksRepository.save(feedbacks);
    }

    public Feedbacks searchFeedbackById(String email, String productName) {
        Users users = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Products products = productService.getProductByName(productName);
        if (products == null) {
            throw new RuntimeException("Product not found");
        }
        String productId = products.getProductId();
        String userId = users.getUserId();
        Feedbacks feedbacks = feedbacksRepository.findByProductIdAndUserId(productId, userId);
        if (feedbacks == null) {
            throw new RuntimeException("Feedback not found");
        }
        return feedbacks;
    }

    public double calculateAverageRating(String productName) {
        String productId = productsRepository.findProductIDByName(productName);
        List<Feedbacks> feedbacks = feedbacksRepository.findByProductId(productId);
        if (feedbacks.isEmpty()) {
            return 0.0;
        }
        int totalRating = 0;
        for (Feedbacks feedback : feedbacks) {
            totalRating += feedback.getRating();
        }
        double averageRating = (double) totalRating / feedbacks.size();
        return averageRating;
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

    public Map<Byte, Integer> getRatingByAllUsers(String productName) {
        String productId = productsRepository.findProductIDByName(productName);
        if(productId == null) {
            throw new RuntimeException("Product not found");
        }
        List<Feedbacks> feedbacks = feedbacksRepository.findByProductId(productId);

        Map<Byte, Integer> rating = new HashMap<>();
        for (byte i = 1; i <= 5; i++){
            rating.put(i, 0);
        }
        for (Feedbacks feedback : feedbacks) {
            byte ratingFeedBack = feedback.getRating();
            rating.put(ratingFeedBack, rating.get(ratingFeedBack) + 1);
        }
        return rating;
    }

    public List<Feedbacks> getFeedbacks() {
        return feedbacksRepository.findAll();
    }
}
