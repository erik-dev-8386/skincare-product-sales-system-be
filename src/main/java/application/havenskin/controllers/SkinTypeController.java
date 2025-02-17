package application.havenskin.controllers;

import application.havenskin.models.SkinTypes;
import application.havenskin.dataAccess.SkinTypeDTO;
import application.havenskin.services.SkinTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/haven-skin/skin-types")
public class SkinTypeController {
    @Autowired
    private SkinTypeService skinTypeService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<SkinTypes> getAllSkinTypes() {
        return skinTypeService.getAllSkinTypes();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping
    public SkinTypes addSkinType(@RequestBody SkinTypes skinTypes) {
        return skinTypeService.createSkinType(skinTypes);
    }
    @GetMapping("/{id}")
    public SkinTypes getSkinTypeById(@PathVariable String id) {
        return skinTypeService.getSkinTypeById(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/{id}")
    public SkinTypes updateSkinType(@PathVariable String id, @RequestBody SkinTypeDTO skinTypes) {
        return skinTypeService.updateSkinType(id, skinTypes);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
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

    @GetMapping("/list-name-skin-types")
    public List<String> listSkinTypeNames() {
        return skinTypeService.getAllSkinTypeNames();
    }
}
