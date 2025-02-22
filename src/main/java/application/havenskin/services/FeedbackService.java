package application.havenskin.services;

import application.havenskin.dataAccess.FeedbackDTO;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Feedbacks;
import application.havenskin.repositories.FeedbacksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbacksRepository feedbacksRepository;
    @Autowired
    private Mapper mapper;
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
    public void deleteFeedback(String id) {
        if(!feedbacksRepository.existsById(id)) {
            throw new RuntimeException("Feedback does not exist");
        }
        feedbacksRepository.deleteById(id);
    }
    public List<Feedbacks> addListOfFeedbacks(List<Feedbacks> feedbacks) {
        return feedbacksRepository.saveAll(feedbacks);
    }
}
