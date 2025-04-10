package application.havenskin.controllers;

import application.havenskin.dataAccess.PlanSkinCareDTO;
import application.havenskin.models.SkinCaresPlan;
import application.havenskin.services.SkinCarePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/plan-skin-cares")
public class PlanSkinCareController {
    @Autowired
    private SkinCarePlanService skinCarePlanService;
    // gọi bên Admin
    @GetMapping
    public List<SkinCaresPlan> getAllSkinCare(){
        return skinCarePlanService.getAllSkinCarePlans();
    }
    // gọi bên trang chủ, người dùng
    @GetMapping("/users")
    public List<SkinCaresPlan> getAllSkinCareByStatus(){
        return skinCarePlanService.getAllSkinCarePlansByStatus();
    }
    // hàm này tạo mới tên lộ trình loại da nhá
    //  gọi hàm show ra các loại da rồi cho người dùng chọn r gửi loại da về cho BE là xong!
    @PostMapping("/{skinCareName}")
    public SkinCaresPlan createSkinCare(@PathVariable String skinCareName, @RequestBody PlanSkinCareDTO planSkinCareDTO) {
        return skinCarePlanService.createPlanSkinCare(skinCareName, planSkinCareDTO);
    }

    @GetMapping("/{skinTypes}")
    public List<SkinCaresPlan> getSkinCarePlan(@PathVariable String skinTypes) {
        return skinCarePlanService.getPlanSkinCare(skinTypes);
    }

    @PutMapping("/{skinTypeName}/{description}")
    public SkinCaresPlan updateSkinCarePlan(@PathVariable String skinTypeName, @PathVariable String description, @RequestBody PlanSkinCareDTO planSkinCareDTO) {
        return skinCarePlanService.updatePlanSkinCares(skinTypeName, description, planSkinCareDTO);
    }

    @DeleteMapping("/{skinName}/{description}")
    public SkinCaresPlan deleteSkinCarePlan(@PathVariable String skinName, @PathVariable String description) {
        return skinCarePlanService.deletePlanSkinCare(skinName, description);
    }

    @GetMapping("/search/{description}")
    public List<SkinCaresPlan> searchSkinCare(@PathVariable String description) {
        return skinCarePlanService.searchSkinCarePlan(description);
    }

    @GetMapping("/show")
    public List<SkinCaresPlan> showSkinCare() {
        return skinCarePlanService.getAllSkinCarePlanDescriptions();
    }

}
