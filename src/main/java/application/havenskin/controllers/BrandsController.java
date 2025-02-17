package application.havenskin.controllers;


import application.havenskin.models.Brands;
import application.havenskin.dataAccess.BrandDTO;
import application.havenskin.repositories.BrandsRepository;
import application.havenskin.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/haven-skin/brands")
public class BrandsController {
    @Autowired
    private BrandService brandService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Brands> getAllBrands() {
        return brandService.getAllBrands();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping
    public Brands createBrand(@RequestBody BrandDTO brand) {
        return brandService.createBrand(brand);
    }
    @GetMapping("/{id}")
    public Brands getBrandById(@PathVariable String id) {
        return brandService.getBrandById(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/{id}")
    public Brands updateBrand(@PathVariable String id, @RequestBody BrandDTO brand) {
        return brandService.updateBrand(id, brand);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @DeleteMapping("/{id}")
    public Brands deleteBrand(@PathVariable String id) {
        return brandService.deleteBrand(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public void deleteBrand() {
        brandService.deleteAllBrands();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
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
    @GetMapping("/list-name-brands")
    public List<String> getBrandsByListName() {
        return brandService.getAllBrandByName();
    }
}
