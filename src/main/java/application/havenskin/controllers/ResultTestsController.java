package application.havenskin.controllers;

import application.havenskin.dataAccess.ResultTestDto;
import application.havenskin.services.ResultTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/result-tests")
public class ResultTestsController {
    @Autowired
    private ResultTestService resultTestService;

    @GetMapping("/id/{id}")
    public ResultTestDto getResultTestDetails(@PathVariable String id) {
        return resultTestService.getResultTestsWithAnswers(id);
    }


    @GetMapping
    public List<ResultTestDto> getAllResultTests() {
        return resultTestService.getAllResultTests();
    }

    @DeleteMapping("/{id}")
    public String deleteResultTest(@PathVariable String id) {
        resultTestService.deleteResultTestById(id);
        return "ResultTest has been deleted";
    }



}
