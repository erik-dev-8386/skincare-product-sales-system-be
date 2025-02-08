package application.havenskin.controllers;

import application.havenskin.models.SkinTypes;
import application.havenskin.services.SkinTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/skin-types")
public class SkinTypeController {
    @Autowired
    private SkinTypeService skinTypeService;
    @GetMapping
    public List<SkinTypes> getAllSkinTypes() {
        return skinTypeService.getAllSkinTypes();
    }

    @PostMapping
    public SkinTypes addSkinType(@RequestBody SkinTypes skinTypes) {
        return skinTypeService.createSkinType(skinTypes);
    }

    @GetMapping("/id/{id}")
    public SkinTypes getSkinTypeById(@PathVariable String id) {
        return skinTypeService.getSkinTypeById(id);
    }

    @GetMapping("/name/{skinName}")
    public SkinTypes getSkinTypeByName(@PathVariable String skinName) {
        return skinTypeService.getSkinTypeByName(skinName);
    }

    @PutMapping("/{id}")
    public SkinTypes updateSkinType(@PathVariable String id, @RequestBody SkinTypes skinTypes) {
        return skinTypeService.updateSkinType(id, skinTypes);
    }

    @DeleteMapping("/{id}")
    public String deleteSkinType(@PathVariable String id) {
        skinTypeService.softDeleteSkinType(id);
        return "SkinType has been deleted successfully";
    }

    @DeleteMapping
    public String deleteAllSkinTypes() {
        skinTypeService.deleteAllSkinTypes();
        return "All SkinTypes deleted";
    }
}
