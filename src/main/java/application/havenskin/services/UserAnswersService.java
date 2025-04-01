package application.havenskin.services;

import application.havenskin.dataAccess.UserAnswerDto;
import application.havenskin.models.UserAnswers;
import application.havenskin.repositories.AnswerRepository;
import application.havenskin.repositories.QuestionsRepository;
import application.havenskin.repositories.UserAnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAnswersService {
    @Autowired
    private UserAnswersRepository userAnswersRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private AnswerRepository answerRepository;


    public List<UserAnswerDto> getUserAnswersByResultTestId(String resultTestId) {
        List<UserAnswers> uaList = userAnswersRepository.findByResultTest_ResultTestId(resultTestId);
        return uaList.stream().map(ua -> {
            UserAnswerDto uad = new UserAnswerDto();
            uad.setUserAnswerId(ua.getUserAnswersId());
            uad.setQuestionId(ua.getQuestionId());
            uad.setAnswerId(ua.getAnswerId());
            uad.setMark(ua.getMark());

            // Lấy nội dung câu hỏi
            questionsRepository.findById(ua.getQuestionId()).ifPresent(question ->
                    uad.setQuestionContent(question.getQuestionContent())
            );

            // Lấy nội dung câu trả lời
            answerRepository.findById(ua.getAnswerId()).ifPresent(answer ->
                    uad.setAnswerContent(answer.getAnswerContent())
            );

            return uad;
        }).collect(Collectors.toList());
    }
}
