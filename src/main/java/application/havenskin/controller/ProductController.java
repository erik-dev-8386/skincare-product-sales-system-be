package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Products;
import application.havenskin.DTORequest.ProductDTO;
import application.havenskin.response.Response;
import application.havenskin.service.BrandService;
import application.havenskin.service.CategoryService;
import application.havenskin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping("/{id}")
    public Response<Products> updateProduct(@PathVariable String id, @RequestBody ProductDTO product) {
        Response<Products> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.updateProduct(id, product));
        return response;
    }
    @GetMapping("/{id}")
    public Response<Products> getProduct(@PathVariable String id) {
        Response<Products> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.getProductById(id));
        return response;
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }
    @PutMapping("/{name}")
    public  Response<List<Products>> getProductByCategoryName(@PathVariable String name) {
        Response<List<Products>> response = new Response<>();
        String id = categoryService.getCategoriesByName(name);
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.getProductsByCategory(id));
        return response;
    }

    @GetMapping("/test/{brandName}")
    public Response<List<Products>> getProductByBrandName(@PathVariable String brandName) {
        Response<List<Products>> response = new Response<>();
        String id = brandService.getBrandsByName(brandName);
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.getProductsByBrand(id));
        return response;
    }
   @PostMapping("/add-list-products")
    public Response<List<Products>> addListProducts(@RequestBody List<Products> x) {
        Response<List<Products>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(productService.addListOfProducts(x));
        return response;
   }
}
