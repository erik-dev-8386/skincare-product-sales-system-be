package application.havenskin.controllers;

import application.havenskin.models.SkinTypes;
import application.havenskin.dataAccess.SkinTypeDTO;
import application.havenskin.services.SkinTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
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
    @GetMapping("/{id}")
    public SkinTypes getSkinTypeById(@PathVariable String id) {
        return skinTypeService.getSkinTypeById(id);
    }
    @PutMapping("/{id}")
    public SkinTypes updateSkinType(@PathVariable String id, @RequestBody SkinTypeDTO skinTypes) {
        return skinTypeService.updateSkinType(id, skinTypes);
    }
    @DeleteMapping("/{id}")
    public void deleteSkinType(@PathVariable String id) {
        skinTypeService.deleteSkinType(id);
    }
    @DeleteMapping
    public void deleteAllSkinTypes() {
        skinTypeService.deleteAllSkinTypes();
    }

    @PostMapping("/add-list-skin-types")
    public List<SkinTypes> addSkinTypeList(@RequestBody List<SkinTypes> skinTypes) {
        return skinTypeService.addListOfSkinTypes(skinTypes);
    }
}
