package application.havenskin.controllers;

import application.havenskin.dataAccess.SkinTestsDto;
import application.havenskin.dataAccess.SubmitTestRequestDto;
import application.havenskin.models.ResultTests;
import application.havenskin.models.SkinTests;
import application.havenskin.services.ResultTestService;
import application.havenskin.services.SkintestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/haven-skin/skin-tests")
public class SkintestsController {
    @Autowired
    private SkintestsService skintestsService;
    @Autowired
    private ResultTestService resultTestService;

//    // 1. Lấy tất cả bài test
//    @GetMapping
//    public List<SkinTests> getAllSkinTests() {
//        return skintestsService.getAllSkinTests();
//    }

//    @GetMapping("/id/{skintestId}")
//    public SkinTests getSkinTestById(@PathVariable String skintestId) {
//        return skintestsService.getSkinTestById(skintestId);
//    }

//    // 3. Tạo bài test mới
    @PostMapping
    public SkinTests createSkinTest(@RequestBody SkinTestsDto skinTest) {
        return skintestsService.createSkinTest(skinTest);
    }

    // 4. Cập nhật bài test
    @PutMapping("/{id}")
    public SkinTests updateSkinTest(@PathVariable String id, @RequestBody SkinTests skinTest) {
        return skintestsService.updateSkinTest(id, skinTest);
    }

    // 5. Xóa mềm bài test (chỉ thay đổi status)
    @DeleteMapping("/{id}")
    public String deleteSkinTest(@PathVariable String id) {
        skintestsService.deleteSkinTest(id);
        return "Skintest has been deleted";
    }

    @GetMapping("/questions-answers/{skintestId}")
    public SkinTestsDto getSkinTestWithQAndA(@PathVariable String skintestId) {
        return skintestsService.getSkinTestWithQuestionsAndAnswers(skintestId);
    }

    //submit bài Test
    @PostMapping("/{skintesId}/submitTest")
    public ResultTests submitTest(@PathVariable String skintesId, @RequestBody SubmitTestRequestDto request) {
        request.setSkinTestId(skintesId);
        return resultTestService.processTestResult(request);
    }

}
