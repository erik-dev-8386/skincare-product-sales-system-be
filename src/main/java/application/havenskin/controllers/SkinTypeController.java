package application.havenskin.controllers;

import application.havenskin.dataAccess.ProductDTO;
import application.havenskin.dataAccess.SkinTypeDTO;
import application.havenskin.models.Products;
import application.havenskin.models.SkinTypes;
import application.havenskin.services.SkinTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/haven-skin/skin-types")
public class SkinTypeController {
    @Autowired
    private SkinTypeService skinTypeService;
    //@PreAuthorize("hasAnyRole('ADMIN','STAFF', 'CUSTOMER')")
    @GetMapping
    public List<SkinTypes> getAllSkinTypes() {
        return skinTypeService.getAllSkinTypes();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
//    @PostMapping
//    public SkinTypes addSkinType(@RequestBody SkinTypeDTO skinTypes) {
//        return skinTypeService.createSkinType(skinTypes);
//    }
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // nhận dữ liệu dưới dạng multipart/form-data
//    public SkinTypes addSkinType(@RequestPart("skinType") SkinTypeDTO skinTypeDTO, @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
//
////        skinTypeDTO.setImages(images);
//        return skinTypeService.createSkinType(skinTypeDTO,images);
//    }
    @PostMapping
    public SkinTypes createSkinType(@Valid @RequestPart("skinTypeDTO") SkinTypeDTO skinTypeDTO, @RequestParam(value = "images", required = false) List<MultipartFile> images) throws IOException {
        SkinTypes x = skinTypeService.createSkinType(skinTypeDTO, images);
        return x;
    }
    @GetMapping("/{id}")
    public SkinTypes getSkinTypeById(@PathVariable String id) {
        return skinTypeService.getSkinTypeById(id);
    }

    @GetMapping("/info/{name}")
    public SkinTypes getSkinTypeByName(@PathVariable String name) {
        return skinTypeService.getSkinTypeByName(name);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/{id}")
    public SkinTypes updateSkinType(@PathVariable String id,@RequestPart("skinTypeDTO") SkinTypeDTO skinTypeDTO,@RequestParam(value = "images", required = false) List<MultipartFile> images) throws IOException {
        return skinTypeService.updateSkinType(id, skinTypeDTO, images);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @DeleteMapping("/{id}")
    public SkinTypes deleteSkinType(@PathVariable String id) {
        return skinTypeService.deleteSkinType(id);
    }
    @DeleteMapping
    public void deleteAllSkinTypes() {
        skinTypeService.deleteAllSkinTypes();
    }

    @PostMapping("/add-list-skin-types")
    public List<SkinTypes> addSkinTypeList(@RequestBody List<SkinTypes> skinTypes) {
        return skinTypeService.addListOfSkinTypes(skinTypes);
    }
    @GetMapping("/name/{skinTypeName}")
    public String getSkinTypeName(@PathVariable String skinTypeName) {
        return skinTypeService.getSkinTypeNameById(skinTypeName);
    }

    @GetMapping("/list-name-skin-types")
    public List<SkinTypes> listSkinTypeNames() {
        return skinTypeService.getAllSkinTypeNames();
    }

    @GetMapping("search/{skin-types}")
    public List<SkinTypes> searchSkinTypes(@PathVariable String skinTypes) {
        return skinTypeService.searchSkinTypes(skinTypes);
    }
}
