package application.havenskin.controllers;

import application.havenskin.dataAccess.QuestionsResponseDto;
import application.havenskin.models.Questions;
import application.havenskin.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/haven-skin/questions")
@RestController

public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<QuestionsResponseDto> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{questionId}")
    public QuestionsResponseDto getQuestionById(@PathVariable String questionId) {
        return questionService.getQuestionsById(questionId);
    }

    @GetMapping("/search")
    public List<QuestionsResponseDto> getQuestionByContent(@RequestParam("keyword") String keyword) {
        return questionService.searchQuestionsByContent(keyword);
    }

    @PostMapping
    public Questions createQuestion(@RequestBody Questions question) {
        return questionService.addQuestion(question);
    }

    @PutMapping("/{questionId}")
    public QuestionsResponseDto updateQuestion(@PathVariable String questionId, @RequestBody QuestionsResponseDto dto) {
        return questionService.updateQuestion(questionId, dto);
    }

    @GetMapping("/find-by-content-question")
    public List<String> findByContent(){
        return questionService.getAllQuestionContent();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Questions deleteQuestion(@PathVariable String id) {
       return questionService.deleteQuestion(id);
//        return "Question has been deleted";
    }
}
