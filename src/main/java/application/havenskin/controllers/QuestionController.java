package application.havenskin.controllers;

import application.havenskin.dataAccess.QuestionsDto;
import application.havenskin.models.Questions;
import application.havenskin.services.QuestionService;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/haven-skin/questions")
@RestController

public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<QuestionsDto> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{questionId}")
    public QuestionsDto getQuestionById(@PathVariable String questionId) {
        return questionService.getQuestionsById(questionId);
    }

    @GetMapping("/search")
    public List<QuestionsDto> getQuestionByContent(@RequestParam("keyword") String keyword) {
        return questionService.searchQuestionsByContent(keyword);
    }

    @PostMapping
    public Questions createQuestion(@RequestBody Questions question) {
        return questionService.addQuestion(question);
    }

    @PutMapping("/{questionId}")
    public QuestionsDto updateQuestion(@PathVariable String questionId,@RequestBody QuestionsDto dto) {
        return questionService.updateQuestion(questionId, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable String id) {
        questionService.deleteQuestion(id);
        return "Question has been deleted";
    }
}
