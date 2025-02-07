package application.havenskin.controller;


import application.havenskin.BusinessObject.Models.Brands;
import application.havenskin.DTORequest.BrandDTO;
import application.havenskin.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/brand")
public class BrandsController {
    @Autowired
    private BrandService brandService;
    @GetMapping
    public List<Brands> getAllBrands() {
        return brandService.getAllBrands();
    }
    @PostMapping
    public Brands createBrand(@RequestBody BrandDTO brand) {
        return brandService.createBrand(brand);
    }
    @GetMapping("/{id}")
    public Brands getBrandById(@PathVariable String id) {
        return brandService.getBrandById(id);
    }
    @PutMapping("/{id}")
    public Brands updateBrand(@PathVariable String id, @RequestBody BrandDTO brand) {
        return brandService.updateBrand(id, brand);
    }
    @DeleteMapping
    public void deleteBrand() {
        brandService.deleteAllBrands();
    }
}
