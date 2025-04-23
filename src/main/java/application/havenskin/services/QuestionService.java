package application.havenskin.services;

import application.havenskin.dataAccess.AnswersDto;
import application.havenskin.dataAccess.QuestionsResponseDto;
import application.havenskin.enums.QuestionEnum;
import application.havenskin.models.Answers;
import application.havenskin.models.Questions;
import application.havenskin.repositories.AnswerRepository;
import application.havenskin.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public List<QuestionsResponseDto> getAllQuestions() {
        List<Questions> questions = questionsRepository.findAll();

        return questions.stream().map(q -> {
            // đẩy nó thành stream và map vào QuestionDTO
            QuestionsResponseDto qDto = new QuestionsResponseDto();
            qDto.setQuestionId(q.getQuestionId());
            qDto.setQuestionContent(q.getQuestionContent());
            qDto.setMaxMark(q.getMaxMark());
            qDto.setStatus(q.getStatus());
            List<Answers> ansList = answerRepository.findByQuestionIdAndStatus(q.getQuestionId(), QuestionEnum.ACTIVE.getStatus());
            List<AnswersDto> ansDtoList = ansList.stream().map(a -> {
                AnswersDto ad = new AnswersDto();
                ad.setAnswerId(a.getAnswerId());
                ad.setAnswerContent(a.getAnswerContent());
                ad.setMark(a.getMark());
                return ad;
            }).collect(Collectors.toList()); // chuyển dạng stream thành List -> Answer

            qDto.setAnswers(ansDtoList);
            return qDto;
        }).collect(Collectors.toList());

    }

    public QuestionsResponseDto getQuestionsById(String questionId) {
        Questions questions =  questionsRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        // Convert question -> questionDTO
        QuestionsResponseDto dto = new QuestionsResponseDto();
        dto.setQuestionId(questions.getQuestionId());
        dto.setQuestionContent(questions.getQuestionContent());
        dto.setMaxMark(questions.getMaxMark());

        // Lấy answers
        List<Answers> answers = answerRepository.findByQuestionIdAndStatus(questions.getQuestionId(), QuestionEnum.ACTIVE.getStatus());
        List<AnswersDto> answerDTOs = answers.stream().map(a -> {
            AnswersDto aDto = new AnswersDto();
            aDto.setAnswerId(a.getAnswerId());
            aDto.setAnswerContent(a.getAnswerContent());
            aDto.setMark(a.getMark());
            return aDto;
        }).collect(Collectors.toList());

        dto.setAnswers(answerDTOs);
        return dto;
    }

    public List<QuestionsResponseDto> searchQuestionsByContent(String keyword) {
        List<Questions> questions = questionsRepository.searchByContent(keyword);

        return questions.stream().map(q -> {
            QuestionsResponseDto dto = new QuestionsResponseDto();
            dto.setQuestionId(q.getQuestionId());
            dto.setQuestionContent(q.getQuestionContent());
            dto.setMaxMark(q.getMaxMark());

            // Nếu muốn trả về answers, load answers từ answersRepository
            // List<Answers> ans = answersRepository.findByQuestionId(q.getQuestionId());
            // dto.setAnswers(... map sang AnswerDTO ...);

            List<Answers> answers = answerRepository.findByQuestionIdAndStatus(q.getQuestionId(), QuestionEnum.ACTIVE.getStatus());
            List<AnswersDto> answerDTOs = answers.stream().map(a -> {
                AnswersDto aDto = new AnswersDto();
                aDto.setAnswerId(a.getAnswerId());
                aDto.setAnswerContent(a.getAnswerContent());
                aDto.setMark(a.getMark());
                return aDto;
            }).collect(Collectors.toList());

            dto.setAnswers(answerDTOs);

            return dto;
        }).collect(Collectors.toList());
    }

    public Questions deleteQuestion(String id) {
        Questions questions = questionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
//        questionsRepository.delete(questions);
//        return "Question has been deleted";
        questions.setStatus(QuestionEnum.INACTIVE.getStatus());
        questionsRepository.save(questions);
        return questions;
    }

    public List<String> getAllQuestionContent() {
        return questionsRepository.findAllQuestionContents();
    }
    public Questions addQuestion(Questions questions) {
        questions.setSkinTestId("1");
        questions.setStatus(QuestionEnum.ACTIVE.getStatus());
        return questionsRepository.save(questions);
    }

    public QuestionsResponseDto updateQuestion(String questionsId, QuestionsResponseDto dto) {
        Questions q = questionsRepository.findById(questionsId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        // update logic
        q.setQuestionContent(dto.getQuestionContent());
        q.setMaxMark(dto.getMaxMark());
        q.setStatus(dto.getStatus());
        questionsRepository.save(q);


        // Map entity -> DTO
        QuestionsResponseDto resultDto = new QuestionsResponseDto();
        resultDto.setQuestionId(q.getQuestionId());
        resultDto.setQuestionContent(q.getQuestionContent());
        resultDto.setMaxMark(q.getMaxMark());
//        resultDto.setStatus(dto.getStatus());
        return resultDto;
    }
}
