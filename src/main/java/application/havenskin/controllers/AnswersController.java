package application.havenskin.controllers;

import application.havenskin.dataAccess.AnswersDto;
import application.havenskin.models.Answers;
import application.havenskin.models.Questions;
import application.havenskin.services.AnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/answers")
public class AnswersController {
    @Autowired
    private AnswersService answersService;

    @GetMapping
    public List<Answers> getAllAnswers() {
        return answersService.getAllAnswers();
    }

    @GetMapping("/{id}")
    public Answers getAnswerById(@PathVariable String id) {
        return answersService.getAnswerById(id);
    }

    @PostMapping
    public Answers createAnswer(@RequestBody Answers answers) {
        return answersService.addAnswer(answers);
    }

    @PutMapping("/{id}")
    public Answers updateAnswer(@PathVariable String id, @RequestBody Answers answers) {
        return answersService.updateAnswer(id, answers);
    }

    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable String id) {
        answersService.deleteAnswer(id);
        return "Answer has been deleted";
    }

    @GetMapping("/questions/{questionsId}")
    public List<Answers> getAnswersByQuestionsId(@PathVariable String questionsId) {
        return answersService.searchAnswersByQuestionId(questionsId);
    }

    @GetMapping("/search")
    public List<Answers> searchAnswers(@RequestParam("keyword") String keyword) {
        return answersService.searchAnswersByContent(keyword);
    }

    @PostMapping("/add-by-question-content")
    public AnswersDto addAnswerByQuesContent(@RequestParam String questionContent, @RequestBody AnswersDto answersDto) {
        return answersService.addAnswerByQuestionContent(questionContent, answersDto);
    }

    @GetMapping("/searchByQuestionContent")
    public List<AnswersDto> getAnswersByQuestionContent(@RequestParam String questionContent) {
        return answersService.getAnswersByQuestionContent(questionContent);
    }

}
