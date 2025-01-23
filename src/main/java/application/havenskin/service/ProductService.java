package application.havenskin.service;

import application.havenskin.BusinessObject.Models.Categories;
import application.havenskin.BusinessObject.Models.Products;
import application.havenskin.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private CategoryService categoryService;

    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    public Products getProductById(String id) {
        return productsRepository.findById(id).get();
    }

    public Products addProduct(Products product) {
        return productsRepository.save(product);
    }

    public Products updateProduct(String id, Products product) {
        if (!productsRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        return productsRepository.save(product);
    }

    public void deleteProduct(String id) {
        productsRepository.deleteById(id);
    }

    public List<Products> getProductsByCategory(String id) {
       if(productsRepository.findByCategoryId(id) == null) {
           throw new RuntimeException("Product not found");
       }
       return productsRepository.findByCategoryId(id);
    }
    public List<Products> getProductsByBrand(String id) {
        if(productsRepository.findByBrandId(id) == null) {
            throw new RuntimeException("Product not found");
        }
        return productsRepository.findByBrandId(id);
    }
}
