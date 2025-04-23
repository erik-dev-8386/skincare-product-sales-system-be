package application.havenskin.services;

import application.havenskin.dataAccess.AnswersDto;
import application.havenskin.enums.AnswerEnum;
import application.havenskin.enums.QuestionEnum;
import application.havenskin.models.Answers;
import application.havenskin.models.Questions;
import application.havenskin.repositories.AnswerRepository;
import application.havenskin.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswersService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionsRepository questionsRepository;


    public List<AnswersDto> getAllAnswers() {
        List<Answers> answers = answerRepository.findAll();
        return answers.stream().map(answer -> {
            AnswersDto dto = new AnswersDto();
            dto.setAnswerId(answer.getAnswerId());
            dto.setAnswerContent(answer.getAnswerContent());
            dto.setMark(answer.getMark());
            dto.setStatus(answer.getStatus());

            // Lấy nội dung câu hỏi từ đối tượng Questions liên kết
            if (answer.getQuestion() != null) {
                dto.setQuestionContent(answer.getQuestion().getQuestionContent());
            } else {
                dto.setQuestionContent(null); // Hoặc giá trị mặc định nếu không có câu hỏi liên kết
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public Answers getAnswerById(String id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No answer found with id: " + id));
    }

    // Tìm Answers gần đúng theo nội dung
    public List<Answers> searchAnswersByContent(String keyword) {
        return answerRepository.findByAnswerContentContainingIgnoreCase(keyword);
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

        existingAnswer.setStatus(answer.getStatus());

        return answerRepository.save(existingAnswer);
    }

    public Answers deleteAnswer(String id) {
        Optional<Answers> optionalAnswers = answerRepository.findById(id);
        if (optionalAnswers.isPresent()) {
            Answers answer = optionalAnswers.get();
            answer.setStatus(AnswerEnum.INACTIVE.getStatus());
            return answerRepository.save(answer);
        }
        throw new RuntimeException("No answer found with id: " + id);
    }

    public List<Answers> searchAnswersByQuestionId(String questionId) {
        return answerRepository.findByQuestionIdAndStatus(questionId, QuestionEnum.ACTIVE.getStatus());
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
        answer.setQuestionId(question.getQuestionId());
        answer.setStatus(AnswerEnum.ACTIVE.getStatus());
        // 3. Lưu đáp án vào database
        answer = answerRepository.save(answer);

        // 4. Chuyển đổi thành DTO để trả về
        AnswersDto responseDto = new AnswersDto();
        responseDto.setAnswerId(answer.getAnswerId());
        responseDto.setAnswerContent(answer.getAnswerContent());
        responseDto.setMark(answer.getMark());
        responseDto.setQuestionContent(question.getQuestionContent());
        return responseDto;
    }

    public List<AnswersDto> getAnswersByQuestionContent(String questionContent) {
        // 1. Tìm câu hỏi theo questionContent
        List<Questions> questions = questionsRepository.findByQuestionContent(questionContent);

        if (questions.isEmpty()) {
            throw new RuntimeException("Không tìm thấy câu hỏi với nội dung: " + questionContent);
        }

        // Chỉ lấy 1 câu hỏi đầu tiên (tránh lỗi nếu có nhiều câu giống nhau)
        Questions question = questions.get(0);

        // 2. Lấy danh sách đáp án của câu hỏi này
        List<Answers> answers = answerRepository.findByQuestion(question);

        // 3. Chuyển đổi danh sách đáp án sang DTO
        return answers.stream().map(a -> {
            AnswersDto dto = new AnswersDto();
            dto.setAnswerId(a.getAnswerId());
            dto.setAnswerContent(a.getAnswerContent());
            dto.setMark(a.getMark());
            return dto;
        }).collect(Collectors.toList());
    }
}
