package application.havenskin.controllers;


import application.havenskin.models.Brands;
import application.havenskin.dataAccess.BrandDTO;
import application.havenskin.repositories.BrandsRepository;
import application.havenskin.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/haven-skin/brands")
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

    @DeleteMapping("/{id}")
    public Brands deleteBrand(@PathVariable String id) {
        return brandService.deleteBrand(id);
    }
    @DeleteMapping
    public void deleteBrand() {
        brandService.deleteAllBrands();
    }
    @PostMapping("/add-list-brands")
    public List<Brands> addListBrands(@RequestBody List<Brands> brands) {
        return brandService.addBrands(brands);
    }
    @GetMapping("/name/{brandName}")
    public Brands getBrandByName(@PathVariable String brandName) {
        return brandService.getBrandByName(brandName);
    }

    @GetMapping("/country/{countryName}")
    public List<Brands> getBrandsByCountry(@PathVariable String countryName) {
        return brandService.getBrandsByCountry(countryName);
    }
}
