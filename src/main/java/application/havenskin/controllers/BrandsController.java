package application.havenskin.controllers;

import application.havenskin.models.Brands;
import application.havenskin.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Brands createBrand(@RequestBody Brands brand) {
        return brandService.createBrand(brand);
    }
    @GetMapping("/id/{id}")
    public Brands getBrandById(@PathVariable String id) {
        return brandService.getBrandById(id);
    }

    @GetMapping("/name/{brandName}")
    public Brands getBrandByName(@PathVariable String brandName) {
        return brandService.getBrandByName(brandName);
    }

    @GetMapping("/country/{countryName}")
    public List<Brands> getBrandsByCountry(@PathVariable String countryName) {
        return brandService.getBrandsByCountry(countryName);
    }

    @PutMapping("/{id}")
    public Brands updateBrand(@PathVariable String id, @RequestBody Brands brand) {
        return brandService.updateBrand(id, brand);
    }

    @DeleteMapping("/{id}")
    public String deleteBrand(@PathVariable String id) {
        brandService.softDeleteBrandsById(id);
        return "Brand has been deleted successfully";
    }


}
