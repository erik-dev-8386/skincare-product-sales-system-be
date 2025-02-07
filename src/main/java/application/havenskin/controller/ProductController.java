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
    public Response<List<Products>> getAllProducts() {
        Response<List<Products>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.getAllProducts());
        return response;
    }

    @PostMapping
    public Response<Products> addProduct(@RequestBody Products product) {
        Response<Products> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.addProduct(product));
        return response;
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
    public Response<Products> updateProduct(@PathVariable String id, @RequestBody Products product) {
        Response<Products> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.updateProduct(id, product));
        return response;
    }

    @GetMapping("/id/{id}")
    public Response<Products> getProductById(@PathVariable String id) {
        Response<Products> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.getProductById(id));
        return response;
    }

    @GetMapping("/name/{productName}")
    public Response<Products> getProductByName(@PathVariable String productName) {
        Response<Products> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.getProductByName(productName));
        return response;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "Product has been deleted successfully";
    }

    //    @PutMapping("/{name}")
//    @PutMapping("/category/{name}")

//    @GetMapping("/category/{categoryName}")
//    public Response<List<Products>> getProductByCategoryName(@PathVariable String name) {
//        Response<List<Products>> response = new Response<>();
//        String id = categoryService.getCategoriesByName(name);
//        response.setCode(200);
//        response.setMessage("OK");
//        response.setResult(productService.getProductsByCategoryId(id));
//        return response;
//    }

    @GetMapping("/category/{categoryName}")
    public Response<List<Products>> getProductByCategoryName(@PathVariable String categoryName) {
        Response<List<Products>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.getProductsByCategoryName(categoryName));
        return response;
    }

    //@GetMapping("/test/{brandName}")
    @GetMapping("/brand/name/{brandName}")
    public Response<List<Products>> getProductByBrandName(@PathVariable String brandName) {
        Response<List<Products>> response = new Response<>();
      //  String id = brandService.getBrandsByName(brandName);
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.getProductsByBrandName(brandName));
        return response;
    }

    @GetMapping("/brand/country/{countryName}")
    public Response<List<Products>> getProductByBrandCountry(@PathVariable String countryName) {
        Response<List<Products>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.getProductsByBrandCountry(countryName));
        return response;
    }

    @GetMapping("/skin-name/{skinName}")
    public Response<List<Products>> getProductsBySkinName(@PathVariable String skinName) {
        Response<List<Products>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.getProductsBySkinName(skinName));
        return response;
    }

    @GetMapping("/discount-name/{discountName}")
    public Response<List<Products>> getProductsByDiscountName(@PathVariable String discountName) {
        Response<List<Products>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.getProductsByDiscountName(discountName));
        return response;
    }



}
