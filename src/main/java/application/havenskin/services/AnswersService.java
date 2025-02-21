package application.havenskin.services;

import application.havenskin.dataAccess.AnswersDto;
import application.havenskin.models.Answers;
import application.havenskin.models.Questions;
import application.havenskin.repositories.AnswerRepository;
import application.havenskin.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswersService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionsRepository questionsRepository;

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

    public AnswersDto addAnswerByQuestionContent(String questionContent, AnswersDto answerDto) {
        // 1. Tìm câu hỏi theo questionContent
        List<Questions> questions = questionsRepository.findByQuestionContent(questionContent);

        if (questions.isEmpty()) {
            throw new RuntimeException("Không tìm thấy câu hỏi với nội dung: " + questionContent);
        }

        // Chỉ lấy 1 câu hỏi đầu tiên trong danh sách (tránh lỗi danh sách nhiều câu hỏi)
        Questions question = questions.get(0);

        // 2. Tạo đáp án mới và liên kết với câu hỏi
        Answers answer = new Answers();
        answer.setAnswerContent(answerDto.getAnswerContent());
        answer.setMark(answerDto.getMark());
        answer.setQuestion(question);  // Gán câu hỏi vào đáp án

        // 3. Lưu đáp án vào database
        answer = answerRepository.save(answer);

        // 4. Chuyển đổi thành DTO để trả về
        AnswersDto responseDto = new AnswersDto();
        responseDto.setAnswerId(answer.getAnswerId());
        responseDto.setAnswerContent(answer.getAnswerContent());
        responseDto.setMark(answer.getMark());

        return responseDto;
    }
}
