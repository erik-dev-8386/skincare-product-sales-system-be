package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Products;
import application.havenskin.DTORequest.ProductDTO;
import application.havenskin.service.BrandService;
import application.havenskin.service.CategoryService;
import application.havenskin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/haven-skin/products")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @GetMapping
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }
    @PostMapping
    public Products addProduct(@RequestBody Products product) {
       return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public Products updateProduct(@PathVariable String id, @RequestBody ProductDTO product) {
       return productService.updateProduct(id, product);
    }
    @GetMapping("/{id}")
    public Products getProduct(@PathVariable String id) {
       return productService.getProductById(id);
    }
    @DeleteMapping("/{id}")
    public Products deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }
    @PutMapping("/{name}")
    public  List<Products> getProductByCategoryName(@PathVariable String name) {
       return productService.getProductsByCategory(name);
    }

    @GetMapping("/test/{brandName}")
    public List<Products> getProductByBrandName(@PathVariable String brandName) {
       return productService.getProductsByBrand(brandName);
    }
   @PostMapping("/add-list-products")
    public List<Products> addListProducts(@RequestBody List<Products> x) {
        return productService.addListOfProducts(x);
   }
   @GetMapping("/max-quantity")
    public List<Products> getMaxQuantity() {
        return productService.getBestSellerProducts();
   }
}
