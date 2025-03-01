package application.havenskin.controllers;

import application.havenskin.dataAccess.ProductDTO;
import application.havenskin.models.Products;
import application.havenskin.services.BrandService;
import application.havenskin.services.CategoryService;
import application.havenskin.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/haven-skin/products")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
   // @PreAuthorize("hasAnyRole('ADMIN','STAFF', 'CUSTOMER')")
    @GetMapping
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping
//    public Products addProduct(@RequestBody ProductDTO product) {
//        return productService.addProduct(product);
//    }
    public Products createProduct(@RequestPart("products") ProductDTO productDTO, @RequestParam("images") List<MultipartFile> images) throws IOException {
        return productService.addProduct(productDTO, images);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/{id}")
    public Products updateProduct(@PathVariable String id, @RequestBody ProductDTO product) {
        return productService.updateProduct(id, product);
    }
    @GetMapping("/{id}")
    public Products getProduct(@PathVariable String id) {
        return productService.getProductById(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @DeleteMapping("/{id}")
    public Products deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/add-list-products")
    public List<Products> addListProducts(@RequestBody List<Products> x) {
        return productService.addListOfProducts(x);
    }
    @GetMapping("/max-quantity")
    public List<Products> getMaxQuantity() {
        return productService.getBestSellerProducts();
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
