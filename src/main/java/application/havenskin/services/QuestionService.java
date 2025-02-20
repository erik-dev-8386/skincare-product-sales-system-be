package application.havenskin.services;

import application.havenskin.dataAccess.AnswersDto;
import application.havenskin.dataAccess.QuestionsDto;
import application.havenskin.models.Answers;
import application.havenskin.models.Products;
import application.havenskin.models.Questions;
import application.havenskin.repositories.AnswerRepository;
import application.havenskin.repositories.QuestionsRepository;
import org.aspectj.weaver.patterns.TypePatternQuestions;
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

    public List<QuestionsDto> getAllQuestions() {
        List<Questions> questions = questionsRepository.findAll();

        return questions.stream().map(q -> {
            QuestionsDto qDto = new QuestionsDto();
            qDto.setQuestionId(q.getQuestionId());
            qDto.setQuestionContent(q.getQuestionContent());
            qDto.setMaxMark(q.getMaxMark());

            List<Answers> ansList = answerRepository.findByQuestionId(q.getQuestionId());
            List<AnswersDto> ansDtoList = ansList.stream().map(a -> {
                AnswersDto ad = new AnswersDto();
                ad.setAnswerId(a.getAnswerId());
                ad.setAnswerContent(a.getAnswerContent());
                ad.setMark(a.getMark());
                return ad;
            }).collect(Collectors.toList());

            qDto.setAnswers(ansDtoList);
            return qDto;
        }).collect(Collectors.toList());

    }

    public QuestionsDto getQuestionsById(String questionId) {
        Questions questions =  questionsRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        // Convert question -> questionDTO
        QuestionsDto dto = new QuestionsDto();
        dto.setQuestionId(questions.getQuestionId());
        dto.setQuestionContent(questions.getQuestionContent());
        dto.setMaxMark(questions.getMaxMark());

        // Lấy answers
        List<Answers> answers = answerRepository.findByQuestionId(questionId);
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

    public List<QuestionsDto> searchQuestionsByContent(String keyword) {
        List<Questions> questions = questionsRepository.searchByContent(keyword);

        return questions.stream().map(q -> {
            QuestionsDto dto = new QuestionsDto();
            dto.setQuestionId(q.getQuestionId());
            dto.setQuestionContent(q.getQuestionContent());
            dto.setMaxMark(q.getMaxMark());

            // Nếu muốn trả về answers, load answers từ answersRepository
            // List<Answers> ans = answersRepository.findByQuestionId(q.getQuestionId());
            // dto.setAnswers(... map sang AnswerDTO ...);

            List<Answers> answers = answerRepository.findByQuestionId(q.getQuestionId());
            List<AnswersDto> answerDTOs = answers.stream().map(a -> {
                AnswersDto aDto = new AnswersDto();
                aDto.setAnswerId(a.getAnswerId());
                aDto.setAnswerContent(a.getAnswerContent());
                aDto.setMark(a.getMark());
                return aDto;
            }).toList();

            return dto;
        }).collect(Collectors.toList());
    }

    public String deleteQuestion(String id) {
        Questions questions = questionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        questionsRepository.delete(questions);
        return "Question has been deleted";
    }

    public Questions addQuestion(Questions questions) {
        return questionsRepository.save(questions);
    }

    public QuestionsDto updateQuestion(String questionsId, QuestionsDto dto) {
        Questions q = questionsRepository.findById(questionsId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        // update logic
        q.setQuestionContent(dto.getQuestionContent());
        q.setMaxMark(dto.getMaxMark());
        questionsRepository.save(q);

        // Map entity -> DTO
        QuestionsDto resultDto = new QuestionsDto();
        resultDto.setQuestionId(q.getQuestionId());
        resultDto.setQuestionContent(q.getQuestionContent());
        resultDto.setMaxMark(q.getMaxMark());
        return resultDto;
    }
}
