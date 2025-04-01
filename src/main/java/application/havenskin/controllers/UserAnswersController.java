package application.havenskin.controllers;

import application.havenskin.dataAccess.UserAnswerDto;
import application.havenskin.services.UserAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
