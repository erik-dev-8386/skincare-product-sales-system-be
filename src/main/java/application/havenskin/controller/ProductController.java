package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Brands;
import application.havenskin.BusinessObject.Models.Products;
import application.havenskin.repository.ProductsRepository;
import application.havenskin.response.Response;
import application.havenskin.service.BrandService;
import application.havenskin.service.CategoryService;
import application.havenskin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/haven-skin/products")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Products addProduct(@RequestBody Products product) {
        return productService.addProduct(product);
    }

    //    @PostMapping("/{id}")
//    public Response<Products> updateProduct(@PathVariable String id, @RequestBody Products product) {
//        Response<Products> response = new Response<>();
//        response.setCode(200);
//        response.setMessage("OK");
//        response.setResult(productService.updateProduct(id, product));
//        return response;
//    }
    @PutMapping("/{id}")
    public Products updateProduct(@PathVariable String id, @RequestBody Products product) {
        return productService.updateProduct(id, product);
    }

    @GetMapping("/id/{id}")
    public Products getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @GetMapping("/name/{productName}")
    public Products getProductByName(@PathVariable String productName) {
        return productService.getProductByName(productName);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.softDeleteProduct(id);
        return "Product has been deleted successfully";
    }

    @GetMapping("/category/{categoryName}")
    public List<Products> getProductByCategoryName(@PathVariable String categoryName) {
        return productService.getProductsByCategoryName(categoryName);
    }

    //@GetMapping("/test/{brandName}")
    @GetMapping("/brand/name/{brandName}")
    public List<Products> getProductByBrandName(@PathVariable String brandName) {
        return productService.getProductsByBrandName(brandName);
    }

    @GetMapping("/brand/country/{countryName}")
    public List<Products> getProductByBrandCountry(@PathVariable String countryName) {
        return productService.getProductsByBrandCountry(countryName);
    }

    @GetMapping("/skin-name/{skinName}")
    public List<Products> getProductsBySkinName(@PathVariable String skinName) {
        return productService.getProductsBySkinName(skinName);
    }

    @GetMapping("/discount-name/{discountName}")
    public List<Products> getProductsByDiscountName(@PathVariable String discountName) {
        return productService.getProductsByDiscountName(discountName);
    }



}
