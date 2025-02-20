package application.havenskin.controllers;

import application.havenskin.dataAccess.UserAnswerDto;
import application.havenskin.models.UserAnswers;
import application.havenskin.services.UserAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/user-answers")
public class UserAnswersController {
    @Autowired
    private UserAnswersService userAnswersService;


    // Lấy tất cả user_answers
    @GetMapping("/result-test/{resultTestId}")
    public List<UserAnswerDto> getAnswersByResultTest(@PathVariable String resultTestId) {
        return userAnswersService.getUserAnswersByResultTestId(resultTestId);
    }

}
