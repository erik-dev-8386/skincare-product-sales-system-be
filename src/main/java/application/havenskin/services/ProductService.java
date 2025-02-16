package application.havenskin.services;

import application.havenskin.dataAccess.ProductDTO;
import application.havenskin.enums.ProductEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Products;
import application.havenskin.repositories.ProductsRepository;
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
    @Autowired
    private Mapper mapper;
    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    public Products getProductById(String id) {
        return productsRepository.findById(id).get();
    }

    public Products addProduct(Products product) {
        return productsRepository.save(product);
    }

    public Products updateProduct(String id, ProductDTO product) {
        Products products = productsRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        mapper.updateProducts(products, product);
        return productsRepository.save(products);
    }

    public Products getProductByName(String name) {
        return productsRepository.findByProductName(name);
    }

    public Products deleteProduct(String id) {
        //productsRepository.deleteById(id);
        Optional<Products> products = productsRepository.findById(id);
        if (products.isPresent()) {
            Products x = products.get();
            x.setStatus(ProductEnums.OUT_OF_STOCK.getValue());
            return productsRepository.save(x);
        }
        return null;
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
    public List<Products> addListOfProducts(List<Products> products) {
        return productsRepository.saveAll(products);
    }

    public List<Products> getBestSellerProducts() {
        int max = productsRepository.findAll().stream().mapToInt(Products::getQuantity).max().orElse(0);
        return productsRepository.findByQuantity(max);
    }
    public List<Products> getProductsByCategoryName(String categoryName) {
        if (productsRepository.findProductsByCategories_CategoryName(categoryName) == null) {
            throw new RuntimeException("Product not found");
        }
        return productsRepository.findProductsByCategories_CategoryName(categoryName);
    }

    public List<Products> getProductsByBrandName(String brandName) {
        if (productsRepository.findByBrands_BrandName(brandName) == null) {
            throw new RuntimeException("Product not found with " + brandName);
        }
        return productsRepository.findByBrands_BrandName(brandName);
    }

    public List<Products> getProductsByBrandCountry(String country){
        if(productsRepository.findByCountry(country) == null){
            throw new RuntimeException("Product not found with " + country);
        }
        return productsRepository.findByCountry(country);
    }

    public List<Products> getProductsBySkinName(String skinName) {
        if (productsRepository.findBySkinName(skinName) == null) {
            throw new RuntimeException("Product not found with " + skinName);
        }
        return productsRepository.findBySkinName(skinName);
    }

    public List<Products> getProductsByDiscountName(String discountName) {
        if (productsRepository.findProductsByDiscounts_DiscountName(discountName) == null) {
            throw new RuntimeException("Product not found with " + discountName);
        }
        return productsRepository.findProductsByDiscounts_DiscountName(discountName);
    }

}
