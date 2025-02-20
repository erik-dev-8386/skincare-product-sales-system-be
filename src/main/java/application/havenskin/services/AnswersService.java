package application.havenskin.services;

import application.havenskin.models.Answers;
import application.havenskin.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswersService {
    @Autowired
    private AnswerRepository answerRepository;

    public List<Answers> getAllAnswers() {
        return answerRepository.findAll();
    }

    public Answers getAnswerById(String id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No answer found with id: " + id));
    }

    // Tìm Answers gần đúng theo nội dung
    public List<Answers> searchAnswersByContent(String keyword) {
        return answerRepository.searchByContent(keyword);
    }

    public Answers addAnswer(Answers answer) {
        return answerRepository.save(answer);
    }

    public Answers updateAnswer(String id, Answers answer) {
        Optional<Answers> optionalAnswers = answerRepository.findById(id);
        Answers existingAnswer = optionalAnswers.orElseThrow(() -> new RuntimeException("No answer found with id: " + id));
        if (answer.getAnswerContent() != null) {
            existingAnswer.setAnswerContent(answer.getAnswerContent());
        }
        if (answer.getMark() != null) {
            existingAnswer.setMark(answer.getMark());
        }
        if (answer.getQuestionId() != null) {
            existingAnswer.setQuestionId(answer.getQuestionId());
        }

        return answerRepository.save(existingAnswer);
    }

    public String deleteAnswer(String id) {
        answerRepository.deleteById(id);
        return "Answer has been deleted";
    }

    public List<Answers> searchAnswersByQuestionId(String questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

}
