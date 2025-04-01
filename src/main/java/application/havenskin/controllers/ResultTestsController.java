package application.havenskin.controllers;

import application.havenskin.dataAccess.ResultTestDto;
import application.havenskin.services.ResultTestService;
import application.havenskin.services.SkinTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/result-tests")
public class ResultTestsController {
    @Autowired
    private ResultTestService resultTestService;

    @Autowired
    private SkinTypeService skinTypeService;
    @GetMapping("/id/{id}")
    public ResultTestDto getResultTestDetails(@PathVariable String id) {
        return resultTestService.getResultTestsWithDetails(id);
    }

    @GetMapping("/email/{email}")
    public List<ResultTestDto> getSkinTestResultByEmail(@PathVariable String email) {
        List<ResultTestDto> resultTestDto = resultTestService.getResultTestByEmail(email);
        return resultTestDto;
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

    @PutMapping("/{id}")
    public ResultTestDto updateResultTest(@PathVariable String id, @RequestBody ResultTestDto resultTestDto) {
        return resultTestService.updateStatus(id, resultTestDto);
    }
}
