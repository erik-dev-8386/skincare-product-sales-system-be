package application.havenskin.controllers;

import application.havenskin.dataAccess.MiniSkinCarePlanDTO;
import application.havenskin.models.MiniSkinCarePlan;
import application.havenskin.services.MiniSkinCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/mini-skin-cares")
public class MiniSkinCarePlanController {
    @Autowired
    private MiniSkinCareService miniSkinCareService;

    // Hàm này gọi bên admin
    @GetMapping
    public List<MiniSkinCarePlan> getAllCarePlans() {
        return miniSkinCareService.getAllMiniSkinCarePlans();
    }

    // hàm này gọi bên users
    @GetMapping("/admin")
    public List<MiniSkinCarePlan> getAllMiniSkinCarePlanByStatus() {
        return miniSkinCareService.getAllMiniSkinCarePlansByStatus();
    }

    @PostMapping("/{description}")
    public MiniSkinCarePlan createCarePlan(@PathVariable String description, @RequestBody MiniSkinCarePlanDTO miniSkinCarePlanDTO) {
        return miniSkinCareService.addMiniSkinCarePlan(description, miniSkinCarePlanDTO);
    }

//    @GetMapping("/{action}")
//    public MiniSkinCarePlan getCarePlan(@PathVariable String action) {
//        return miniSkinCareService.getMiniSkinCarePlan(action);
//    }

    @PutMapping("/{action}/{description}")
    public MiniSkinCarePlan updateMiniSkinCarePlan(@PathVariable String action,@PathVariable String description, @RequestBody MiniSkinCarePlanDTO miniSkinCarePlan) {
        return miniSkinCareService.updateMiniSkinCarePlan(action, description,miniSkinCarePlan);
    }

    @DeleteMapping("/{action}/{description}")
    public MiniSkinCarePlan deleteCarePlan(@PathVariable String action, @PathVariable String description) {
        return miniSkinCareService.deleteMiniSkinCarePlan(action, description);
    }
    // lấy theo description của skincareplan
    @GetMapping("/{description}")
    public List<MiniSkinCarePlan> getCarePlanByDescription(@PathVariable String description) {
        return miniSkinCareService.getMiniPlansByDescription(description);
    }
    @GetMapping("/info/{action}")
    public List<MiniSkinCarePlan> getCarePlanByAction(@PathVariable String action) {
        return miniSkinCareService.searchMiniSkinCarePlan(action);
    }
}
