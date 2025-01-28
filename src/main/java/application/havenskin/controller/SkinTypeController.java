package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.SkinTypes;
import application.havenskin.DTORequest.SkinTypeDTO;
import application.havenskin.response.Response;
import application.havenskin.service.SkinTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/skin-types")
public class SkinTypeController {
    @Autowired
    private SkinTypeService skinTypeService;
    @GetMapping
    public Response<List<SkinTypes>> getAllSkinTypes() {
        Response<List<SkinTypes>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(skinTypeService.getAllSkinTypes());
        return response;
    }
    @PostMapping
    public Response<SkinTypes> addSkinType(@RequestBody SkinTypes skinTypes) {
        Response<SkinTypes> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(skinTypeService.createSkinType(skinTypes));
        return response;
    }
    @GetMapping("/{id}")
    public Response<SkinTypes> getSkinTypeById(@PathVariable String id) {
        Response<SkinTypes> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(skinTypeService.getSkinTypeById(id));
        return response;
    }
    @PutMapping("/{id}")
    public Response<SkinTypes> updateSkinType(@PathVariable String id, @RequestBody SkinTypeDTO skinTypes) {
        Response<SkinTypes> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(skinTypeService.updateSkinType(id, skinTypes));
        return response;
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
    public Response<List<SkinTypes>> addSkinTypeList(@RequestBody List<SkinTypes> skinTypes) {
        Response<List<SkinTypes>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(skinTypeService.addListOfSkinTypes(skinTypes));
        return response;
    }
}
