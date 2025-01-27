package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Brands;
import application.havenskin.response.Response;
import application.havenskin.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/brands")
public class BrandsController {
    @Autowired
    private BrandService brandService;
    @GetMapping
    public Response<List<Brands>> getAllBrands() {
        Response<List<Brands>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(brandService.getAllBrands());
        return response;
    }
    @PostMapping
    public Response<Brands> createBrand(@RequestBody Brands brand) {
        Response<Brands> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(brandService.createBrand(brand));
        return response;
    }
    @GetMapping("/id/{id}")
    public Response<Brands> getBrandById(@PathVariable String id) {
        Response<Brands> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(brandService.getBrandById(id));
        return response;
    }

    @GetMapping("/name/{brandName}")
    public Response<Brands> getBrandByName(@PathVariable String brandName) {
        Response<Brands> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(brandService.getBrandByName(brandName));
        return response;
    }

    @GetMapping("/country/{countryName}")
    public Response<List<Brands>> getBrandsByCountry(@PathVariable String countryName) {
        Response<List<Brands>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(brandService.getBrandsByCountry(countryName));
        return response;
    }

    @PutMapping("/{id}")
    public Response<Brands> updateBrand(@PathVariable String id, @RequestBody Brands brand) {
        Response<Brands> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(brandService.updateBrand(id, brand));
        return response;
    }
    @DeleteMapping("/{id}")
    public String deleteBrand(@PathVariable String id) {
        brandService.deleteBrandsById(id);
        return "Brand has been deleted successfully";
    }


}
