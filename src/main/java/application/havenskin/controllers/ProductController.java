package application.havenskin.controllers;

import application.havenskin.dataAccess.ProductDTO;
import application.havenskin.models.Products;
import application.havenskin.services.BrandService;
import application.havenskin.services.CategoryService;
import application.havenskin.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Products createProduct(@Valid @RequestPart("products") ProductDTO productDTO, @RequestParam(value = "images", required = false) List<MultipartFile> images) throws IOException {
        return productService.addProduct(productDTO, images);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/{id}")
    public Products updateProduct(@PathVariable String id, @RequestPart("products") ProductDTO productDTO, @RequestParam(value = "images", required = false) List<MultipartFile> images) throws IOException {
        return productService.updateProduct(id, productDTO, images);
    }

    @GetMapping("/{id}")
    public Products getProduct(@PathVariable String id) {
        return productService.getProductById(id);
    }

    //    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Products deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/get-product-name-by-id/{productName}")
    public String getProductByName(@PathVariable String productName) {
        return productService.getProductIDByName(productName);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/add-list-products")
    public List<Products> addListProducts(@Valid @RequestBody List<Products> x) {
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

    @GetMapping("/compare-product/{productsName}")
    public Products compareProduct(@PathVariable String productsName) {
        return productService.compareProducts(productsName);
    }

    @GetMapping("/best-seller")
    public List<Products> getBestSellerProducts() {
        return productService.getBestSellerProducts();
    }

    @GetMapping("/search/{productName}")
    public List<Products> getProductByProductName(@PathVariable String productName) {
        return productService.searchProducts(productName);
    }

    @GetMapping("/sort/{startPrice}/{endPrice}")
    public List<Products> sortProductsByPrice(@PathVariable double startPrice, @PathVariable double endPrice) {
        return productService.sortDiscountPrice(startPrice, endPrice);
    }
//    @GetMapping("/pagingProducts")
//    public Page<Products> getProducts(@RequestParam(defaultValue = "0") int page,
//                                      @RequestParam(defaultValue = "5") int size) {
//        return productService.getProducts(page, size);
//    }

    @GetMapping("/list-name-products")
    public List<Products> getListProducts() {
        return productService.findByAllProducts();
    }



}
